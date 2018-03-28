package org.gamelog.controller;

import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.service.GameService;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String registerPlayer(@ModelAttribute Player player, Model model){
        playerService.save(player);
        return "result";
    }

    // Temporary path to check the database
    @GetMapping(path="/players")
    public @ResponseBody Iterable<Player> getAllPlayers(Model model){
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
    public String getGamePage(@PathVariable("gameid") String gameid, Model model){
        Game game = gameService.getGameInfoById(Long.parseLong(gameid));
        System.out.println(game.getCoverUrl());
        model.addAttribute("game", game);
        return "game";
    }
}