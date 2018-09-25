package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.gamelog.service.GameService;
import org.gamelog.service.GamelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
public class GameController {
    @Autowired
    GameService gameService;

    @Autowired
    EntryService entryService;

    @Autowired
    GamelistService gamelistService;

    /**
     * Fetches the home page.
     * @return A ModelAndView instance containing the "index" template.
     */
    @GetMapping(path = "/")
    private ModelAndView getIndex() {
        return new ModelAndView("index");
    }

    /**
     * Performs a search for games.
     * @param query The string to search for.
     * @param page An integer defining the page to obtain.
     * @return A Future containing a ModelAndView, whose view is the "searchresults.html" template and the model
     * contains a list of 10 games, the current page and the query, for possible future searches.
     */
    @GetMapping(path="search")
    private Future<ModelAndView> getSearch(@RequestParam("query") String query,
                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        return gameService.search(query, page).thenApply(games -> {
            ModelAndView modelAndView = new ModelAndView("searchresults");
            modelAndView.addObject("games", games);
            modelAndView.addObject("query", query);
            modelAndView.addObject("page", page);
            return modelAndView;
        });
    };

    /**
     * Gets the game page, complete with data about the user's entry and lists, as well as public entries and lists.
     * @param gameid The game to get.
     * @param authentication Current authenticated user (if any).
     * @return A completable future containing a model and view with the template "game.html" with model data about the
     * game, user entries and lists, and public entries and lists.
     */
    @GetMapping(path="/game/{gameid}")
    private Future<ModelAndView> getGamePage(@PathVariable("gameid") Long gameid, Authentication authentication){
        return gameService.findGameInfoById(gameid).thenCompose(game -> {

            ModelAndView modelAndView = new ModelAndView("game");
            modelAndView.addObject("game", game);
            CompletableFuture futurePublicEntries = entryService.findPublicEntriesForGameById(game)
                    .thenAccept(entries -> modelAndView.addObject("entries", entries));
            // Only add the following objects to the model if user is authenticated
            if (authentication != null) {
                Player player = (Player) authentication.getPrincipal();
                CompletableFuture futureEntry = entryService.findByPlayerAndGame(player, game)
                        .thenAccept(entry -> modelAndView.addObject("entry", entry));
                CompletableFuture futureGamelists = gamelistService.findAllByPlayerId(player)
                        .thenAccept(gamelists -> {
                            if (gamelists == null) {
                                modelAndView.addObject("hasLists", false);
                            } else {
                                modelAndView.addObject("hasLists", true);
                                modelAndView.addObject("myValidLists", gamelistService.validate(gamelists, game));
                            }
                        });
                CompletableFuture[] completableFutures = {futurePublicEntries, futureEntry, futureGamelists};
                return CompletableFuture.allOf(completableFutures).thenApply(__ -> modelAndView);
            } else {
                return futurePublicEntries.thenApply(__ -> modelAndView);
            }
        });
    }
}