package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.GamelistService;
import org.gamelog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/lists")
public class GamelistController {
    @Autowired
    GamelistService gamelistService;

    @Autowired
    TagService tagService;

    /**
     * Gets all lists for one user.
     * @param authentication Currently authenticated user.
     * @return A model and view for "lists.html".
     */
    @GetMapping
    private Future<ModelAndView> getUserLists(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("lists");
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.findAllByPlayerId(player).thenApply(gamelists -> {
            modelAndView.addObject("lists", gamelists);
            return modelAndView;
        });
    }

    /**
     * Adds a new list to a user.
     * @param authentication Currently authenticated user.
     * @param listname New list's name.
     * @return A redirect to "/lists".
     */
    // TODO: Make it ajax.
    @PostMapping
    private Future<RedirectView> postNewList(Authentication authentication, @RequestParam("listname") String listname) throws Exception {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.addNewList(player, listname).thenApply(gamelist -> new RedirectView("/lists"));
    }

    /**
     * Adds a tag to an existing list.
     * @param tagname The tag to add.
     * @param listname The list's name.
     * @param authentication Currently authenticated user.
     * @return A redirect to the list being tagged.
     */
    // TODO: Make it AJAX.
    @PostMapping(path = "/tag")
    private Future<RedirectView> tagList(@RequestParam("tagname") String tagname, @RequestParam("listname") String listname, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.addTagToList(player, listname, tagname).thenApply(gamelist -> new RedirectView("/lists/" + listname));
    }

    /**
     * Removes an existing tag from an existing list.
     * @param tagname The name of the tag to remove.
     * @param listname The name of the list to remove the tag from.
     * @param authentication Currently authenticated user.
     * @return A redirect to "/list/{listname}"
     */
    @PostMapping(path = "/untag")
    private Future<RedirectView> untagList(@RequestParam("tagname") String tagname, @RequestParam("listname") String listname, Authentication authentication){
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.removeTagFromlist(player, listname, tagname).thenApply(gamelist -> new RedirectView("/lists/" + listname));
    }

    /**
     * Gets data on a user's list.
     * @param listname The name of the list.
     * @param authentication Currently authenticated user.
     * @return Model and view for template "view.html".
     */
    @GetMapping(path = "/{listname}")
    private Future<ModelAndView> getList(@PathVariable("listname") String listname, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.findOneByPlayerAndListName(player, listname).thenCompose(gamelist -> {
            ModelAndView modelAndView = new ModelAndView("list");
            modelAndView.addObject("list", gamelist);
            return tagService.findAllTagsForGamelist(gamelist).thenApply(tags -> {
                modelAndView.addObject("tags", tags);
                return modelAndView;
            });
        });
    }

    /**
     * Deletes an existing list.
     * @param listname The name of the list to be deleted.
     * @param authentication Currently authenticated user.
     * @return A redirect to "/lists"
     */
    @PostMapping(path = "/delete")
    private Future<RedirectView> deleteList(@RequestParam("listname") String listname, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.deleteList(player, listname).thenApply(gamelist -> new RedirectView("/lists"));
    }

    /**
     * Adds a new game to a list.
     * @param listname The name of the list being modified.
     * @param gameid The game being added.
     * @param authentication Currently authenticated user.
     * @return A redirect to "/game/{gameid}"
     */
    @PostMapping(path = "/addGame")
    private CompletableFuture<RedirectView> addGameToList(@RequestParam("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.addGameToList(player, listname, gameid).thenApply(gamelist -> new RedirectView("/game/" + gameid));
    }

    /**
     * Removes an existing game from a list.
     * @param listname The name of the list being modified.
     * @param gameid The game being removed.
     * @param authentication Currently authenticated user.
     * @return A redirect to "list/{listname}".
     */
    @PostMapping(path = "/removeGame")
    private CompletableFuture<RedirectView> removeGameFromlist(@RequestParam("listname") String listname, @RequestParam("gameid") Long gameid, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        return gamelistService.deleteGameFromList(player, listname, gameid).thenApply(game -> new RedirectView("/lists/" + listname));
    }
}
