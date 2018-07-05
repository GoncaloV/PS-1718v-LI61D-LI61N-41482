package org.gamelog.controller;

import org.gamelog.model.Entry;
import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.gamelog.service.GameService;
import org.gamelog.service.GamelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(path = "/")
    private Future<String> getIndex(Model model) {
        CompletableFuture<String> future = new CompletableFuture<>();
        gameService.findRecentGames().thenAccept(games -> {
            model.addAttribute("games", games);
            future.complete("index");
        });
        return future;
    }

    @GetMapping(path="search")
    private Future<String> search(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model){
        CompletableFuture<String> future = new CompletableFuture<>();
        gameService.search(query, page).thenAccept(games -> {
            model.addAttribute("games", games);
            future.complete("searchresults");
        });
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        return future;
    }

    @GetMapping(path="/game/{gameid}")
    private Future<String> getGamePage(@PathVariable("gameid") Long gameid, Model model, Authentication authentication){
        CompletableFuture<String> future = new CompletableFuture();
        gameService.findGameInfoById(gameid).thenAccept(game -> {
            model.addAttribute("game", game);
            if(authentication != null) {
                Player player = (Player) authentication.getPrincipal();
                Entry entry = entryService.findByPlayerAndGame(player, game);
                model.addAttribute("entry", entry);
                model.addAttribute("mylists", gamelistService.findAllByPlayerId(player));
            }
            model.addAttribute("entries", entryService.findPublicEntriesForGameById(game));
            model.addAttribute("lists", gamelistService.findAllByGameId(game));
            future.complete("game");
        });
        return future;
    }
}