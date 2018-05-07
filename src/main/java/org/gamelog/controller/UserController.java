package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path="/register")
    public String registerPlayer(HttpServletRequest request, @ModelAttribute Player player){
        Player p = playerService.save(player);
        if(p != null) {
            try {
                request.login(p.getName(), p.getPassword());
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
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
