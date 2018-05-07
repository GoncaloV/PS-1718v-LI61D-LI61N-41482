package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path="/register")
    public String registerPlayer(@ModelAttribute Player player){
        playerService.save(player);
        return "index";
    }

    @GetMapping(path="/register")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping(path="/login")
    public String getLoginPage(){
        return "login";
    }
}
