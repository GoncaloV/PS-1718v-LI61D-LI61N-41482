package pt.ps.gamelog;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pt.ps.gamelog.Model.Entities.Player;
import pt.ps.gamelog.Model.Repos.PlayerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PlayerTests {
    @Autowired
    private PlayerRepository playerRepository;

	@Test
	public void createPlayer(){
        Player p1 = new Player();
        p1.setName("TestName");
        p1.setPassword("TestPassword");
        playerRepository.save(p1);
	}

	@Test
    public void findPlayer(){
        Player p1 = new Player();
        p1.setName("TestName");
        p1.setPassword("TestPassword");
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());
        assert p1.getName().equals(p2.getName());
        assert p1.getPassword().equals(p2.getPassword());
    }

    @Test
    public void updatePlayer(){
	    // First create a new player
        Player p1 = new Player();
        p1.setName("InitialName");
        p1.setPassword("InitialPassword");
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());

        // Then update it
        p1.setName("UpdatedName");
        p1.setPassword("UpdatedPassword");
        playerRepository.save(p1);
        p2 = playerRepository.findOne(p1.getId());
        assert p2.getName().equals(p1.getName());
        assert p2.getPassword().equals(p1.getPassword());
    }

    @Test
    public void deletePlayer() {
        // First create a new player
        Player p1 = new Player();
        p1.setName("TestName");
        p1.setPassword("TestPassword");
        playerRepository.save(p1);
        assert playerRepository.findOne(p1.getId()) != null;

        // Then delete it
        playerRepository.delete(p1);
        assert playerRepository.findOne(p1.getId()) == null;
    }
}
