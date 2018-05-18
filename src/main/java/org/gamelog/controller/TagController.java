package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    private String getTags(Authentication authentication, Model model){
        Player p = (Player) authentication.getPrincipal();
        model.addAttribute("tags", tagService.findAllTagsByPlayer(p));
        return "tags";
    }

    @PostMapping("/tags")
    private RedirectView postTag(Authentication authentication, @RequestParam(name = "tagname") String tagname){
        Player p = (Player) authentication.getPrincipal();
        tagService.addTagToPlayer(p, tagname);
        return new RedirectView("/tags");
    }

    @PostMapping("/tag/{tagname}/delete")
    private RedirectView deleteTag(Authentication authentication, @PathVariable(name = "tagname") String tagname){
        Player p = (Player) authentication.getPrincipal();
        tagService.delete(p, tagname);
        return new RedirectView("/tags");
    }
}
