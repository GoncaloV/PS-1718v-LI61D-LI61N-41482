package org.gamelog.controller;

import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.service.GameService;
import org.gamelog.service.PlayerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("player", new Player("", ""));
        return "index";
    }

    @PostMapping(path="/player")
    public String registerPlayer(@ModelAttribute Player player){
        playerService.save(player);
        return "result";
    }

    // Temporary path to check the database
    @GetMapping(path="/players")
    public @ResponseBody Iterable<Player> getAllPlayers(){
        return playerService.findAll();
    }

    @GetMapping(path="/login")
    public String getLogin(Model model){
        return "login";
    }

    @GetMapping(path="/diary")
    public String getDiaryPage(){
        return "diary";
    }

    @GetMapping(path="/game/{gameid}")
    public Future<String> getGamePage(@PathVariable("gameid") String gameid, Model model){
        CompletableFuture<String> future = new CompletableFuture();
        gameService.getGameInfoById(Long.parseLong(gameid)).thenAccept(game -> {
            model.addAttribute("game", game);
            future.complete("game");
        });
        return future;
    }

    @GetMapping(path="search")
    public Future<String> search(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model){
        CompletableFuture<String> future = new CompletableFuture<>();
        gameService.search(query, page).thenAccept(games -> {
            model.addAttribute("games", games);
            future.complete("searchresults");
        });
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        return future;
    }
}