package org.gamelog.controller;

import org.gamelog.model.Entry;
import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@Controller
public class DiaryController {
    @Autowired
    EntryService entryService;

    @GetMapping(path="/diary")
    private String  getDiaryPage(Authentication authentication, Model model){
        Player player = (Player) authentication.getPrincipal();
        Iterable<Entry> entries = entryService.findAllEntriesForPlayerById(player);
        model.addAttribute("entries", entries);
        return "diary";
    }

    @PostMapping(path="entry")
    private RedirectView postEntry(@RequestParam(value = "rating", required = false) Integer rating,
                                  @RequestParam(value = "review", required = false) String review,
                                  @RequestParam(value = "favorite", required = false) boolean favorite,
                                  @RequestParam(value = "secret", required = false) boolean secret,
                                  @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                  @RequestParam("gameid") long gameid,
                                  Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        entryService.addEntryForPlayer(rating, review, favorite, secret, date, gameid, player);
        return new RedirectView("/game/" + gameid);
    }
}
