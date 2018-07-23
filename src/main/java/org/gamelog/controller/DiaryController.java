package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Controller
public class DiaryController {
    @Autowired
    EntryService entryService;

    /**
     * Gets the diary page.
     * @param authentication Currently authenticated user.
     * @return A model and view for diary.html.
     */
    @GetMapping(path="/diary")
    private CompletableFuture<ModelAndView> getDiary(Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("diary");
        return entryService.findAllEntriesForPlayerById(player).thenApply(entries -> {
            modelAndView.addObject("entries", entries);
            return modelAndView;
        });
    }

    // TODO: MAKE IT AJAX
//    @PostMapping(path="entry")
//    private RedirectView postEntry(@RequestParam(value = "rating", required = false) Integer rating,
//                                  @RequestParam(value = "review", required = false) String review,
//                                  @RequestParam(value = "favorite", required = false) boolean favorite,
//                                  @RequestParam(value = "secret", required = false) boolean secret,
//                                  @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
//                                  @RequestParam("gameid") long gameid,
//                                  Authentication authentication){
//        Player player = (Player) authentication.getPrincipal();
//        if (entryService.saveEntry(rating, review, favorite, secret, date, gameid, player) != null);
//        return new RedirectView("/game/" + gameid);
//    }

    @PostMapping(path="entry")
    private CompletableFuture<ModelAndView> postEntry_AJAX(@RequestParam(value = "rating", required = false) Integer rating,
                                   @RequestParam(value = "review", required = false) String review,
                                   @RequestParam(value = "favorite", required = false) boolean favorite,
                                   @RequestParam(value = "secret", required = false) boolean secret,
                                   @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                   @RequestParam("gameid") long gameid,
                                   Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return entryService.saveEntry(rating, review, favorite, secret, date, gameid, player).thenApply(entry -> {
            ModelAndView modelAndView = new ModelAndView("entry");
            modelAndView.addObject("entry", entry);
            return modelAndView;
        });
    }
}
