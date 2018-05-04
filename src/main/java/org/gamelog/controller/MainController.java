package org.gamelog.controller;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.gamelog.service.GameService;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private EntryService entryService;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("player", new Player("", ""));
        return "index";
    }

    @PostMapping(path="/register")
    public String registerPlayer(@ModelAttribute Player player){
        playerService.save(player);
        return "index";
    }

    @GetMapping(path="/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping(path="/diary")
    public String  getDiaryPage(Authentication authentication, Model model){
        Player player = (Player) authentication.getPrincipal();
        Iterable<Entry> entries = entryService.findAllEntriesForPlayerById(player);
        LinkedList<Long> ids = new LinkedList<>();
        entries.forEach(e -> {
            ids.add(e.getId().getGame().getId());
        });
        Iterable<Game> games = gameService.findGamesWithIds(ids);
        model.addAttribute("entries", entries);
        model.addAttribute("games", games);
        return "diary";
    }

    @GetMapping(path="/game/{gameid}")
    public Future<String> getGamePage(@PathVariable("gameid") long gameid, Model model){
        CompletableFuture<String> future = new CompletableFuture();
        gameService.getGameInfoById(gameid).thenAccept(game -> {
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

    @PostMapping(path="entry")
    public RedirectView postEntry(@RequestParam(value = "rating", required = false) Integer rating,
                                  @RequestParam(value = "review", required = false) String review,
                                  @RequestParam(value = "favorite", required = false) boolean favorite,
                                  @RequestParam(value = "secret", required = false) boolean secret,
                                  @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                  @RequestParam("gameid") long gameid,
                                  Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        entryService.addEntryForPlayer(rating, review, favorite, secret, date, gameid, player);
        return new RedirectView("diary");
    }
}