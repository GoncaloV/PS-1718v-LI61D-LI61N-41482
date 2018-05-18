package org.gamelog.service;

import org.gamelog.model.*;
import org.gamelog.repos.GamelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class GamelistService {
    @Autowired
    GamelistRepository gamelistRepository;

    @Autowired
    GameService gameService;

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
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        Tag tag = new Tag(player, tagname);
        gamelist.addTag(tag);
        return gamelistRepository.save(gamelist);
    }

    public Iterable<Gamelist> removeTagFromLists(Set<Gamelist> gamelists, Tag tag) {
        gamelists.forEach(gamelist -> {
            gamelist.removeTag(tag);
        });
        return gamelistRepository.save(gamelists);
    }

    public Gamelist removeTagFromList(Player player, String listname, String tagname) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        gamelist.getTags().forEach(tag -> {
            if(tag.getId().getName().equals(tagname))
                gamelist.removeTag(tag);
        });
        return gamelistRepository.save(gamelist);
    }
}
