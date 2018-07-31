package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return playerService.createPlayer(name, password).thenApply(player -> player == null);
    }

    /**
     * Custom substitute for a POST to /login.
     * @param request Request object used to log the user in.
     * @param username Username credentials.
     * @param password Password credentials.
     * @return 404 if invalid credentials, 200 if valid credentials.
     */
    @PostMapping(path = "/attemptlogin")
    @ResponseBody
    public CompletableFuture<ResponseEntity<String>> attemptLogin(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password){
        return playerService.findByName(username).thenApply(player -> {
            if (player == null || !player.getPassword().equals(password))
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            else {
                try {
                    request.login(username, password);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                return new ResponseEntity<String>(HttpStatus.OK);
            }
        });
    }
}
