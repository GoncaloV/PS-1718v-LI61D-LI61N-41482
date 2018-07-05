package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
public class UserController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path="/register")
    private Future<RedirectView> registerPlayer(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("password") String password){
        if (name.length() > 20 || name.length() <= 0) {
            RedirectView redirectView = new RedirectView("register");
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", "Invalid username length. Username length must be between 1 and 20 characters long.");
        }
        return playerService.createPlayer(name, password).thenApply(p -> {
            if(p != null) {
                try {
                    request.login(p.getName(), p.getPassword());
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }
            return new RedirectView("/");
        });
    }

    @GetMapping(path="/register")
    private Object getRegisterPage(Authentication authentication){
        if(authentication != null)
            return new RedirectView("/");
        return new ModelAndView("register");
    }

    @GetMapping(path="/login")
    private Object getLoginPage(Authentication authentication){
        if(authentication != null)
            return new RedirectView("/");
        return new ModelAndView("login");    }
}
