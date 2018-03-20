package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private static PlayerRepository playerRepository;

    public static boolean savePlayer(Player p) {
        try {
            playerRepository.save(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Iterable<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public static Player getPlayerById(long id){
        return playerRepository.findOne(id);
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        PlayerService.playerRepository = playerRepository;
    }
}
