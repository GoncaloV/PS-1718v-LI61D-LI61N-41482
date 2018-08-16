package org.gamelog;

import org.apache.tomcat.jni.Local;
import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.service.EntryService;
import org.gamelog.service.GameService;
import org.gamelog.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class EntryTests {
    @Autowired
    private EntryService entryService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;

    @Test
    @Transactional
    @Async
    public void createEntryTest() throws Exception {
        // TODO: Ask teacher: Async testing?
        Player p = playerService.createPlayer("TESTPLAYER", "TESTPASSWORD").join();
        Game g = new Game(1);
        LocalDate now = LocalDate.now();
        Entry e1 = entryService.saveEntry(10, "review-test", true, true, now, g.getId(), p).join();
        assertNotNull(e1);
        assertEquals(e1.getRating().intValue(), 10);
        assertEquals(e1.getReview(), "review-test");
        assertEquals(e1.isFavorite(), true);
        assertEquals(e1.isPrivate(), true);
        assertEquals(e1.getDate().getYear(), now.getYear());
        assertEquals(e1.getDate().getMonth(), now.getMonth());
        assertEquals(e1.getDate().getDayOfMonth(), now.getDayOfMonth());
        assertEquals(e1.getId().getGame().getId(), g.getId());
        assertEquals(e1.getId().getPlayer().getId(), p.getId());
    }

    @Test
    @Transactional
    @Async
    public void editEntryTest() throws Exception {
        Player p = playerService.createPlayer("TESTPLAYER", "TESTPASSWORD").join();
        Game g = new Game(1);
        LocalDate now = LocalDate.now();
        Entry e = entryService.saveEntry(10, "review-test", true, true, now, g.getId(), p).join();
        assertTrue(entryService.exists(e).join());

        e.setRating(5);
        e.setReview("test-review");
        e.setFavorite(false);
        e.setPrivate(false);
        e.setDate(now.plusDays(1));
        Entry e2 = entryService.saveEntry(e.getRating(), e.getReview(), e.isFavorite(), e.isPrivate(), now, g.getId(), p).join();

        assertNotNull(e2);
        assertEquals(e2.getRating().intValue(), 5);
        assertEquals(e2.getReview(), "test-review");
        assertEquals(e2.isFavorite(), false);
        assertEquals(e2.isPrivate(), false);
        assertEquals(e2.getDate(), now.plusDays(1));
    }
}
