package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path="/register")
    private RedirectView registerPlayer(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("password") String password){
        Player p = playerService.createPlayer(name, password);
        if(p != null) {
            try {
                request.login(p.getName(), p.getPassword());
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        return new RedirectView("/");
    }

    @GetMapping(path="/register")
    private String getRegisterPage(){
        return "register";
    }

    @GetMapping(path="/login")
    private String getLoginPage(){
        return "login";
    }
}
