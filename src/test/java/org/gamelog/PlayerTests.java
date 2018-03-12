package org.gamelog;

import org.gamelog.repos.PlayerRepository;
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
public class PlayerTests {
    @Autowired
    private PlayerRepository playerRepository;

    private final String NAME = "N", PASSWORD = "P", UPDATED_NAME = "N2", UPDATED_PASSWORD = "P2";

	@Test
	public void createPlayer(){
	    // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());

        // Assert
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerRepository.deleteAll();
	}

	@Test
    public void findPlayer(){
        // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());

        // Assert
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerRepository.deleteAll();
    }

    @Test
    public void updatePlayer(){
	    // Setup
        // First create a player
        Player p1 = new Player(NAME, PASSWORD);
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());
        // Ensure values are correct
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Then update it
        p1.setName(UPDATED_NAME); // NOTE: In a real situation, a player will not be able to change their name.
        p1.setPassword(UPDATED_PASSWORD);
        playerRepository.save(p1);
        p2 = playerRepository.findOne(p1.getId());

        // Assert
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerRepository.deleteAll();
	}

    @Test
    public void deletePlayer() {
        // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerRepository.save(p1);

        // Ensure creation succeeds
        assert playerRepository.findOne(p1.getId()) != null;

        // Then delete it
        playerRepository.delete(p1);

        // Assert
        assert playerRepository.findOne(p1.getId()) == null;

        // Cleanup
        playerRepository.deleteAll();
    }
}
