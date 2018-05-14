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
        model.addAttribute("tags", tagService.findAllTagsByPlayer(player));
        return "lists";
    }

    @PostMapping(path = "/lists")
    private RedirectView postNewList(Authentication authentication, @RequestParam("listname") String listname) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addNewList(player, listname);
        return new RedirectView("/lists");
    }

    @GetMapping(path = "/list/{listid}")
    private String getUserLists(@PathVariable("listid") Long listid, Authentication authentication, Model model) {
        Player player = (Player) authentication.getPrincipal();
        model.addAttribute("list", gamelistService.findOneByPlayerAndListId(player, listid));
        return "list";
    }

    @PostMapping(path = "/list/{listid}/delete")
    private RedirectView deleteList(@PathVariable("listid") Long listid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteList(player, listid);
        return new RedirectView("/lists/");
    }

    @PostMapping(path = "/list")
    private RedirectView postToList(@RequestParam("listid") Long listid, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addToList(player, listid, gameid);
        return new RedirectView("/game/" + gameid);
    }

    @PostMapping(path = "/list/{listid}/deletegame")
    private RedirectView deleteGameFromlist(@PathVariable("listid") Long listid, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteGameFromList(player, listid, gameid);
        return new RedirectView("/list/" + listid);
    }
}
