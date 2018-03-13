package org.gamelog;

import org.gamelog.model.Game;
import org.gamelog.repos.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.gamelog.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class GameTests {
    @Autowired
    private GameRepository gameRepository;

    private final long ID = 1;

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
}
