package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.xml.ws.RespectBindingFeature;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    EntryService entryService;

    /**
     * Gets the diary page.
     * @param authentication Currently authenticated user.
     * @return A model and view for diary.html.
     */
    @GetMapping
    private Future<ModelAndView> getDiary(Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("diary");
        return entryService.findAllEntriesForPlayerById(player).thenApply(entries -> {
            modelAndView.addObject("entries", entries);
            return modelAndView;
        });
    }

    /**
     * Posts a new entry or updates an existing one. TODO: Make it AJAX
     * @param rating A rating from 1 to 10 for the game being written about.
     * @param review A string describing the player's experience with the game.
     * @param favorite Whether or not the game is marked as favorite.
     * @param secret Whether or not the game is marked as private.
     * @param date The date when the entry was written.
     * @param gameid The id for the game being written about.
     * @param authentication Request parameter to identify current user.
     * @return A future containing a model and view with the entry.html template, containing data from the entry.
     */
    @PostMapping
    private Future<RedirectView> postEntry(@RequestParam(value = "rating", required = false) Integer rating,
                                                @RequestParam(value = "review", required = false) String review,
                                                @RequestParam(value = "favorite", required = false) boolean favorite,
                                                @RequestParam(value = "secret", required = false) boolean secret,
                                                @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                                @RequestParam("gameid") long gameid,
                                                Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return entryService.saveEntry(rating, review, favorite, secret, date, gameid, player).thenApply(entry -> new RedirectView("/game/" + gameid));
    }

    @PostMapping("/postentryajax")
    @ResponseBody
    private Future<ResponseEntity<String>> postEntryAjax(@RequestParam(value = "rating", required = false) Integer rating,
                                           @RequestParam(value = "review", required = false) String review,
                                           @RequestParam(value = "favorite", required = false) boolean favorite,
                                           @RequestParam(value = "secret", required = false) boolean secret,
                                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                           @RequestParam("gameid") long gameid,
                                           Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return entryService.saveEntry(rating, review, favorite, secret, date, gameid, player)
                .thenApply(__ -> new ResponseEntity<String>(HttpStatus.OK))
                .exceptionally(e -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Deletes an existing entry.
     * @param gameid The game the entry is writing about.
     * @param authentication Request parameter to identify current user.
     * @return A completion stage containing a response entity that tells if the request was completed successfully or exceptionally.
     */
    @PostMapping(path="delete")
    private CompletionStage<ResponseEntity> deleteEntry(@RequestParam("gameid") Long gameid, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return entryService.deleteEntry(player, gameid)
                .thenApply(x -> new ResponseEntity(HttpStatus.OK))
                .exceptionally(y -> new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
