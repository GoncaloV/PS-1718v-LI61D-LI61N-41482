package org.gamelog.controller;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.service.GamelistService;
import org.gamelog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.concurrent.Future;

@Controller
public class GamelistController {
    @Autowired
    GamelistService gamelistService;

    @Autowired
    TagService tagService;

    @GetMapping(path = "/lists")
    private Future<ModelAndView> getUserLists(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("lists");
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.findAllByPlayerId(player).thenApply(gamelists -> {
            modelAndView.addObject("lists", gamelists);
            return modelAndView;
        });
    }

    // TODO: Make it ajax.
    @PostMapping(path = "/lists")
    private Future<RedirectView> postNewList(Authentication authentication, @RequestParam("listname") String listname) {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.addNewList(player, listname).thenApply(gamelist -> new RedirectView("/lists"));
    }

    // TODO: Make lists public or private. Change URIs so that other users can access lists.
    @PostMapping(path = "/list/{listname}/addTag")
    private RedirectView tagList(@RequestParam("tagname") String tagname, @PathVariable("listname") String listname, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addTagToList(player, listname, tagname);
        return new RedirectView("/list/" + listname);
    }

    @PostMapping(path = "/list/{listname}/removeTag")
    private RedirectView untagList(@RequestParam("tagname") String tagname, @PathVariable("listname") String listname, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        gamelistService.removeTagFromlist(player, listname, tagname);
        return new RedirectView("/list/" + listname);
    }

    @GetMapping(path = "/list/{listname}")
    private ModelAndView getList(@PathVariable("listname") String listname, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("list");
        Player player = (Player) authentication.getPrincipal();
        Gamelist gamelist = gamelistService.findOneByPlayerAndListName(player, listname).join(); // TODO: FIX JOIN
        modelAndView.addObject("list", gamelist);
        modelAndView.addObject("tags", tagService.findAllTagsForGamelist(gamelist).join());
        return modelAndView;
    }

    @PostMapping(path = "/list/{listname}/deleteTag")
    private RedirectView deleteList(@PathVariable("listname") String listname, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteList(player, listname);
        return new RedirectView("/lists/");
    }

    @PostMapping(path = "/list")
    private RedirectView addGameToList(@RequestParam("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.addGameToList(player, listname, gameid);
        return new RedirectView("/game/" + gameid);
    }

    @PostMapping(path = "/list/{listname}/deletegame")
    private RedirectView removeGameFromlist(@PathVariable("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        gamelistService.deleteGameFromList(player, listname, gameid);
        return new RedirectView("/list/" + listname);
    }
}
