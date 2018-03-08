package pt.ps.gamelog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.ps.gamelog.Model.Entities.Player;
import pt.ps.gamelog.Model.Repos.PlayerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamelogApplicationTests {
    @Autowired
    private PlayerRepository playerRepository;

	@Test
	public void createPlayer(){
	    Player p1 = new Player(); // Id is automatically generated
        p1.setName("Test Player 1");
        p1.setPassword("PasswordP1");
        playerRepository.save(p1);
        Player p2 = playerRepository.findOne(p1.getId());
        assert p1.getName().equals(p2.getName());
        assert p1.getPassword().equals(p2.getPassword());
	}

	@Test
    public void findPlayer(){

    }
}
