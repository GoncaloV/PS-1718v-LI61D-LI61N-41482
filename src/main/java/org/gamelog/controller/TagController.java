package org.gamelog.controller;

import org.gamelog.model.Player;
import org.gamelog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CompletableFuture;

@Controller
public class TagController {
    @Autowired
    TagService tagService;

    /**
     * Shows all the tags in the database.
     * @return A ModelAndView instance. Model contains all the tags in the database. View is tags.html.
     * @TODO Determine if pagination is necessary and implement it if it is.
     */
    @GetMapping("/tags")
    private CompletableFuture<ModelAndView> getAllTags(){
        return tagService.getAllTags().thenApply(tags -> {
            ModelAndView modelAndView = new ModelAndView("tags");
            modelAndView.addObject("tags", tags);
            return modelAndView;
        });
    }
}
