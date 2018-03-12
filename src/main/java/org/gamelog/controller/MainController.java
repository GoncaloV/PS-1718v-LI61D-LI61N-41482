package org.gamelog.controller;

import org.gamelog.model.Player;
import org.springframework.ui.Model;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path="")
    public String getIndex(Model model) {
        model.addAttribute("player", new Player("DEFAULT", "DEFAULT"));
        return "index";
    }

    @PostMapping(path="/player")
    public String createPlayer(@ModelAttribute Player player){
        playerRepository.save(player);
        return "result";
    }
}