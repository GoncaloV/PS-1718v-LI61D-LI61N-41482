package org.gamelog;

import org.gamelog.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.gamelog.model.Player;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PlayerTests {
    @Autowired
    private PlayerService playerService;

    private final String NAME = "N", PASSWORD = "P", UPDATED_NAME = "N2", UPDATED_PASSWORD = "P2";

    @Test
    @Transactional
    public void testFindOneById(){
        Player p = playerService.createPlayer(NAME, PASSWORD);
        assert playerService.findById(p.getId()) != null;
    }

    @Test
    @Transactional
    public void testFindOneById_Async_Warmup(){
        playerService.createPlayer_async(NAME, PASSWORD).thenAccept(p1 -> {
            playerService.findById_async(p1.getId()).thenAccept(p2 -> {
                assertSame(p1, p2);
            });
        });
    }

    @Test
    @Transactional
    public void testFindOneById_Async(){
        playerService.createPlayer_async(NAME, PASSWORD).thenAccept(p1 -> {
            playerService.findById_async(p1.getId()).thenAccept(p2 -> {
                assertSame(p1, p2);
            });
        });
    }
}
