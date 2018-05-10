package org.gamelog.service;

import org.gamelog.model.*;
import org.gamelog.repos.GamelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Gamelist findOneByPlayerAndListId(Player player, Long listid) {
        return gamelistRepository.findOneByIdPlayerAndIdId(player, listid);
    }

    public Gamelist addNewList(Player player, String name) {
        Gamelist gamelist = new Gamelist(player, name);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist addToList(Player player, Long listid, Long gameid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdId(player, listid);
        Game game = gameService.findGameById(gameid);
        gamelist.addGame(game);
        return gamelistRepository.save(gamelist);
    }

    public Gamelist deleteGameFromList(Player player, Long listid, Long gameid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdId(player, listid);
        Game game = gameService.findGameById(gameid);
        gamelist.removeGame(game);
        return gamelistRepository.save(gamelist);
    }

    public void deleteList(Player player, Long listid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdId(player, listid);
        gamelistRepository.delete(gamelist.getId());
    }
}
