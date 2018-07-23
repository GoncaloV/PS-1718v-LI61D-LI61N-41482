package org.gamelog.controller;

import org.gamelog.model.Entry;
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
    private Future<ModelAndView> getSearch(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page){
        ModelAndView modelAndView = new ModelAndView("searchresults");
        CompletableFuture<ModelAndView> future = gameService.search(query, page).thenApply(games -> {
            modelAndView.addObject("games", games);
            return modelAndView;
        });
        modelAndView.addObject("query", query);
        modelAndView.addObject("page", page);
        return future; // TODO: Show this to teacher. Is "initiating" the async call as soon as possible like this a good idea? Compare to alternative below. Maybe not thread safe?

/*        return gameService.search(query, page).thenApply(games -> {
            ModelAndView modelAndView = new ModelAndView("searchresults");
            modelAndView.addObject("games", games);
            modelAndView.addObject("query", query);
            modelAndView.addObject("page", page);
            return modelAndView;
        });*/
    }

    /**
     * TODO: Another parallel async question.
     * @param gameid
     * @param authentication
     * @return
     */
    @GetMapping(path="/game/{gameid}")
    private Future<ModelAndView> getGamePage(@PathVariable("gameid") Long gameid, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("game");
        return gameService.findGameInfoById(gameid).thenApply(game -> {
            modelAndView.addObject("game", game);
            if(authentication != null) {
                Player player = (Player) authentication.getPrincipal();
                Entry entry = entryService.findByPlayerAndGame(player, game).join(); // TODO: Fix join.
                modelAndView.addObject("entry", entry);
                modelAndView.addObject("mylists", gamelistService.findAllByPlayerId(player).join());
            }
            modelAndView.addObject("entries", entryService.findPublicEntriesForGameById(game).join());
            modelAndView.addObject("lists", gamelistService.findAllByGameId(game).join());
            return modelAndView;
        });
    }
}