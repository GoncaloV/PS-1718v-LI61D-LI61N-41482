package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
public class UserController {
    @Autowired
    PlayerService playerService;

    /**
     * Gets the page for registration.
     * @param authentication Current authenticated user.
     * @return A redirect to the home page if the user is authenticated, or the register page otherwise.
     */
    @GetMapping(path="/register")
    private Object getRegister(Authentication authentication){
        if(authentication != null)
            return new RedirectView("/");
        return new ModelAndView("register");
    }

    /**
     * Gets the page for logging in.
     * @param authentication Current authenticated user.
     * @return A redirect to the home page if the user is authenticated, or the login page otherwise.
     */
    @GetMapping(path="/login")
    private Object getLogin(Authentication authentication){
        if(authentication != null)
            return new RedirectView("/");
        return new ModelAndView("login");
    }

    /**
     * Check if a username already exists and creates a user under that username if it's not taken.
     * @param name The username to register a new user with.
     * @param password The password to register a new user with.
     * @return True if the user was created successfully, false if the username is taken.
     */
    @PostMapping(path="/register")
    @ResponseBody
    public CompletableFuture<Boolean> postRegister(@RequestParam("name") String name, @RequestParam("password") String password){
        return playerService.createPlayer(name, password).thenApply(player -> player != null);
    }
}
