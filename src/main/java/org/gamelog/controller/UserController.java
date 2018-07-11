package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

/*    @PostMapping(path="/register")
    private Future<RedirectView> registerPlayer(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("password") String password){
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
    }*/

    @PostMapping(path="/register")
    @ResponseBody
    public CompletableFuture<?> register(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("password") String password){
        return playerService.findByName(name).thenApply(player -> {
            if (player != null)
                return player; // Player with this name already exists. Returning it signals the AJAX that registration failed.
            else {
               return  playerService.createPlayer(name, password).thenApply(player1 -> null);
            }
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
        return new ModelAndView("login");
    }

/*    *//**
     * TODO: REMOVE ONCE DONE TESTING AJAX
     * @return
     *//*
    @GetMapping("/testAjax")
    public @ResponseBody Player testAjax() {
        return playerService.findByName("ABC").join();
    }*/
}
