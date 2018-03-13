package org.gamelog;

import org.gamelog.model.Game;
import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.repos.GameRepository;
import org.gamelog.repos.GamelistRepository;
import org.gamelog.repos.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class GamelistTests {
    @Autowired
    private GamelistRepository gamelistRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    private final long GAME_ID = 1;

    @Test
    public void createGamelist(){
        try {
            // Setup
            Player p1 = new Player("P1", "PASS1");
            playerRepository.save(p1);
            Gamelist gl1 = new Gamelist(p1, "LIST1");
            gamelistRepository.save(gl1);
            
            // Assert
            assert gamelistRepository.findOne(gl1.getId()) != null;
        } finally {
            // Cleanup
            gamelistRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }
}
