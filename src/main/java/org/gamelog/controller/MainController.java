package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class MainController {
    @GetMapping(path="")
    public String getIndex(Model model) {
        model.addAttribute("player", new Player("", ""));
        return "index";
    }

    @PostMapping(path="/player")
    public String registerPlayer(@ModelAttribute Player player, Model model){
            if(PlayerService.savePlayer(player)){
                return "result";
            }
            model.addAttribute("error", "Error: failed to create user.");
            return "index";
    }

    // Temporary path to check the database
    @GetMapping(path="/players")
    public @ResponseBody Iterable<Player> getAllPlayers(Model model){
        return PlayerService.getAllPlayers();
    }

    @GetMapping(path="/login")
    public String login(Model model){
        return "login";
    }
}