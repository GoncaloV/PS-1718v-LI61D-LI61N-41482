package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.GamelistService;
import org.gamelog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GamelistController {
    @Autowired
    GamelistService gamelistService;

    @Autowired
    TagService tagService;

    @GetMapping(path = "/lists")
    private String getUserLists(Authentication authentication, Model model) {
        Player player = (Player) authentication.getPrincipal();
        model.addAttribute("lists", gamelistService.findAllByPlayerId(player));
        return "lists";
    }

    @PostMapping(path = "/lists")
    private RedirectView postNewList(Authentication authentication, @RequestParam("listname") String listname) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addNewList(player, listname);
        return new RedirectView("/lists");
    }

//    @PostMapping(path = "/list/addTag")
//    private RedirectView tagList(@RequestParam("tagname") String tagname, @RequestParam("listname") String listname, Authentication authentication){
//        Player player = (Player) authentication.getPrincipal();
//        gamelistService.addTagToList(player, listname, tagname);
//        return new RedirectView("/lists");
//    }

    @PostMapping(path = "/list/removeTag")
    private RedirectView untagList(@RequestParam("tagname") String tagname, @RequestParam("listname") String listname, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return new RedirectView("/lists");
    }

    @GetMapping(path = "/list/{listname}")
    private String getList(@PathVariable("listname") String listname, Authentication authentication, Model model) {
        Player player = (Player) authentication.getPrincipal();
        model.addAttribute("list", gamelistService.findOneByPlayerAndListName(player, listname));
        return "list";
    }

    @PostMapping(path = "/list/{listname}/delete")
    private RedirectView deleteList(@PathVariable("listname") String listname, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteList(player, listname);
        return new RedirectView("/lists/");
    }

    @PostMapping(path = "/list")
    private RedirectView postToList(@RequestParam("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addGameToList(player, listname, gameid);
        return new RedirectView("/game/" + gameid);
    }

    @PostMapping(path = "/list/{listname}/deletegame")
    private RedirectView deleteGameFromlist(@PathVariable("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteGameFromList(player, listname, gameid);
        return new RedirectView("/list/" + listname);
    }
}
