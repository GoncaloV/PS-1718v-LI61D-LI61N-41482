package org.gamelog;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.repos.EntryRepository;
import org.gamelog.repos.GameRepository;
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
public class EntryTests {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void createEntry() {
        // Setup
        Player p = new Player("P1", "PASS1");
        playerRepository.save(p);
        Game g = new Game();
        gameRepository.save(g);
        Entry e1 = new Entry(p, g);
        entryRepository.save(e1);

        // Assert
        assert entryRepository.findOne(e1.getId()) != null;

        // Cleanup
        entryRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void findEntry() {
        // Setup
        Player p = new Player("P1", "PASS1");
        playerRepository.save(p);
        Game g = new Game();
        gameRepository.save(g);
        Entry e1 = new Entry(p, g);
        entryRepository.save(e1);

        // Assert
        assert entryRepository.findOne(e1.getId()).equals(e1);

        // Cleanup
        entryRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void updateEntry() {
        // Setup
        Player p = new Player("P1", "PASS1");
        playerRepository.save(p);
        Game g = new Game();
        gameRepository.save(g);
        Entry e1 = new Entry(p, g);
        e1.setFavorite(true);
        e1.setPrivate(false);
        e1.setRating(10);
        e1.setReview("Best Game.");
        entryRepository.save(e1);
        Entry e2 = entryRepository.findOne(e1.getId());

        assert e2.equals(e1);
        assert e2.getRating() == e1.getRating();
        assert e2.getReview().equals(e1.getReview());
        assert e2.isFavorite() == e1.isFavorite();
        assert e2.isPrivate() == e1.isPrivate();

        // Set new values
        e1.setReview("Actually it's mediocre.");
        e1.setRating(5);
        e1.setFavorite(false);
        e1.setPrivate(true);
        entryRepository.save(e1);

        e2 = entryRepository.findOne(e1.getId());

        // Assert
        // Assert both entities are equal by comparing their unique combination of game and player ids
        assert e2.equals(e1);
        // But just to make sure, compare their other data as well
        assert e2.getRating() == e1.getRating();
        assert e2.getReview().equals(e1.getReview());
        assert e2.isFavorite() == e1.isFavorite();
        assert e2.isPrivate() == e1.isPrivate();

        // Cleanup
        entryRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void deleteEntry() {
        // Setup
        Player p = new Player("P1", "PASS1");
        playerRepository.save(p);
        Game g = new Game();
        gameRepository.save(g);
        Entry e1 = new Entry(p, g);
        entryRepository.save(e1);

        assert entryRepository.findOne(e1.getId()) != null;

        entryRepository.delete(e1.getId());

        //Assert
        assert entryRepository.findOne(e1.getId()) == null;

        // Cleanup
        entryRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }
}
