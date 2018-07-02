package org.gamelog.service;

import org.gamelog.model.*;
import org.gamelog.repos.GamelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GamelistService {
    @Autowired
    private GamelistRepository gamelistRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private TagService tagService;

    public Iterable<Gamelist> findAllByPlayerId(Player player){
        return gamelistRepository.findTop5ByIdPlayer(player);
    }

    public Iterable<Gamelist> findAllByGameId(Game game) {
        return gamelistRepository.findTop5ByGames(game);
    }

    public Gamelist findOneByPlayerAndListName(Player player, String listname) {
        return gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
    }

    public Gamelist addNewList(Player player, String name) {
        Gamelist gamelist = new Gamelist(player, name);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist addGameToList(Player player, String listname, Long gameid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        Game game = gameService.findGameById(gameid);
        gamelist.addGame(game);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist deleteGameFromList(Player player, String listname, Long gameid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        Game game = gameService.findGameById(gameid);
        gamelist.removeGame(game);
        return gamelistRepository.save(gamelist);
    }

    public void deleteList(Player player, String listname) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        gamelistRepository.delete(gamelist.getId());
    }

    public Gamelist addTagToList(Player player, String listname, String tagname) {
        Tag t = tagService.findTag(tagname);
        t = t == null ? tagService.createTag(tagname) : t;
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        gamelist.addTag(t);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist addTagToList(Player player, Gamelist gamelist, Tag tag) {
        gamelist.addTag(tag);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist addTagsToList(Player player, Gamelist gamelist, Iterable<Tag> tags) {
        for (Tag t :
                tags) {
            gamelist.addTag(t);
        }
        return gamelistRepository.save(gamelist);
    }

    public Iterable<Gamelist> removeTagFromLists(Set<Gamelist> gamelists, Tag tag) {
        gamelists.forEach(gamelist -> {
            gamelist.removeTag(tag);
        });
        return gamelistRepository.save(gamelists);
    }

    public Gamelist removeTagFromlist(Player player, String listname, String tagname) {
        Tag t = tagService.findTag(tagname);
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        gamelist.removeTag(t);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist findOne(Gamelist gamelist) {
        return gamelistRepository.findOne(gamelist.getId());
    }
}
