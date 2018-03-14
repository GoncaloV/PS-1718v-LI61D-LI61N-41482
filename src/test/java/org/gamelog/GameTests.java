package org.gamelog;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.repos.EntryRepository;
import org.gamelog.repos.GameRepository;
import org.gamelog.repos.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.gamelog.model.Player;

import java.util.ArrayList;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class GameTests {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private EntryRepository entryRepository;

    private final long ID = 1;
    private final String PLAYER_NAME = "PL", PLAYER_PASSWORD = "PW1";
    @Test
    public void createGame(){
        try {
            // Setup
            Game game1 = new Game(ID);
            gameRepository.save(game1);

            // Assert
            assert gameRepository.findOne(game1.getId()) != null;
        } finally {
            // Cleanup
            gameRepository.deleteAll();
        }
    }

    @Test
    public void findGame(){
        try {
            // Setup
            Game game1 = new Game(ID);
            gameRepository.save(game1);

            // Assert
            assert gameRepository.findOne(game1.getId()) != null;
        } finally {
            // Cleanup
            gameRepository.deleteAll();
        }
    }

    @Test
    public void deleteGame() {
        try {
            // Setup
            Game game1 = new Game(ID);
            gameRepository.save(game1);

            assert gameRepository.findOne(game1.getId()) != null;

            gameRepository.delete(game1.getId());

            //Assert
            assert gameRepository.findOne(game1.getId()) == null;
        } finally {
            // Cleanup
            gameRepository.deleteAll();
        }
    }

    @Test
    public void calculateFavorites() {
        try{
            //Setup
            Game game = new Game(ID);
            Player player = new Player(PLAYER_NAME, PLAYER_PASSWORD);
            gameRepository.save(game);
            playerRepository.save(player);
            Entry e1 = new Entry(player, game);
            e1.setFavorite(true);
            entryRepository.save(e1);
            game = gameRepository.findOne(game.getId());

            // Assert
            assert game.getFavorites() == 1;
        } finally {
            entryRepository.deleteAll();
            gameRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }

    @Test
    public void calculateRating(){
        try{
            // Setup
            Game game = new Game(ID);
            gameRepository.save(game);
            // Reminder: Each player can only have one entry per game. The rating average has to come from entries for the same game, from different players.
            ArrayList<Player> players = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                players.add(new Player(PLAYER_NAME + i, PLAYER_PASSWORD));
            }
            playerRepository.save(players);
            LinkedList<Entry> entries = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                entries.addFirst(new Entry(players.get(i), game));
                entries.getFirst().setRating(i);
            }
            entryRepository.save(entries);
            game = gameRepository.findOne(game.getId());

            // Assert
            assert game.getAverageRatings() == (5+4+3+2+1)/6.0;
        } finally {
            entryRepository.deleteAll();
            gameRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }
}
