package org.gamelog;

import org.gamelog.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.gamelog.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PlayerTests {
    @Autowired
    private PlayerService playerService;

    private final String NAME = "N", PASSWORD = "P", UPDATED_NAME = "N2", UPDATED_PASSWORD = "P2";

	@Test
	public void createPlayer(){
	    // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerService.save(p1);
        Player p2 = playerService.findById(p1.getId());

        // Assert
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerService.deleteAll();
	}

	@Test
    public void findPlayerById(){
        // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerService.save(p1);
        Player p2 = playerService.findById(p1.getId());

        // Assert
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerService.deleteAll();
    }

    @Test
    public void updatePlayer(){
	    // Setup
        // First create a player
        Player p1 = new Player(NAME, PASSWORD);
        playerService.save(p1);
        Player p2 = playerService.findById(p1.getId());
        // Ensure values are correct
        assert p2.getPassword().equals(p1.getPassword());

        // Then update it
        p1.setPassword(UPDATED_PASSWORD);
        playerService.save(p1);
        p2 = playerService.findById(p1.getId());

        // Assert
        assert p2.getPassword().equals(p1.getPassword());

        // Cleanup
        playerService.deleteAll();
	}

    @Test
    public void deletePlayer() {
        // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerService.save(p1);

        // Ensure creation succeeds
        assert playerService.findById(p1.getId()) != null;

        // Then delete it
        playerService.delete(p1);

        // Assert
        assert playerService.findById(p1.getId()) == null;

        // Cleanup
        playerService.deleteAll();
    }

    @Test
    public void findPlayerByName(){
        // Setup
        Player p1 = new Player(NAME, PASSWORD);
        playerService.save(p1);

        Player p2 = playerService.findByName(p1.getName());

        // Assert
        assert p1.getId() == p2.getId();
        assert p1.getPassword().equals(p2.getPassword());

        // Cleanup
        playerService.deleteAll();
    }
}
