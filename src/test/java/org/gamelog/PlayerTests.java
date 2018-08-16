package org.gamelog;

import org.gamelog.model.Player;
import org.gamelog.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PlayerTests {
    @Autowired
    private PlayerService playerService;

    private final String NAME = "N", PASSWORD = "P", NAME2 = "N2", PASSWORD2 = "P2",
            UPDATED_NAME = "N2", UPDATED_PASSWORD = "P2";

    @Test
    @Transactional
    public void testFindOneById() throws Exception {
        playerService.createPlayer(NAME, PASSWORD).thenAccept(p1 -> {
            playerService.findById(p1.getId()).thenAccept(p2 -> {
                assertSame(p1, p2);
            });
        });
    }

    @Test
    @Transactional
    public void testFindOneByName() throws Exception {
        AtomicReference<Player> p1 = new AtomicReference<>();
        AtomicReference<Player> p2 = new AtomicReference<>();
        playerService.createPlayer(NAME, PASSWORD).thenAccept(x1 -> {
            p1.set(x1);
            playerService.findByName(NAME).thenAccept(x2 -> {
                p2.set(x2);
            });
        }).join();
        assertSame(p1.get(), p2.get());
    }

    @Test
    @Transactional
    public void testDeleteOne() throws Exception {
        playerService.createPlayer(NAME, PASSWORD).thenAccept(p1 -> {
            playerService.delete(p1);
            playerService.findById(p1.getId()).thenAccept(p2 -> {
                assertNull(p2);
            });
        });
    }

    @Test
    @Transactional
    public void testDeleteAll() throws Exception {
        playerService.createPlayer(NAME, PASSWORD)
                .thenAcceptBoth(playerService.createPlayer(NAME2, PASSWORD2), (p1, p2) -> {
                   playerService.deleteAll();
                   playerService.findAll().thenAccept(players -> {
                       assertIterableEquals(players, Collections.emptyList());
                   });
                });
    }
}
