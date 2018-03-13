package org.gamelog;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Tag;
import org.gamelog.repos.PlayerRepository;
import org.gamelog.repos.TagRepository;
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
public class TagTests {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void createTag(){
        try {
            // Setup
            Player p1 = new Player("P1", "PASS1");
            playerRepository.save(p1);
            Tag t1 = new Tag(p1, "TESTTAG");
            tagRepository.save(t1);

            // Assert
            assert tagRepository.findOne(t1.getId()) != null;
        } finally {
            // Cleanup
            tagRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }

    @Test
    public void findTag(){
        try {
            // Setup
            Player p1 = new Player("P1", "PASS1");
            playerRepository.save(p1);
            Tag t1 = new Tag(p1, "TESTTAG");
            tagRepository.save(t1);

            // Assert
            assert tagRepository.findOne(t1.getId()).equals(t1);
        } finally {
            // Cleanup
            tagRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }

    @Test
    public void updateTag(){
        try {
            // Setup
            Player p = new Player("P1", "PASS1");
            playerRepository.save(p);
            Tag t1 = new Tag(p, "TESTTAG");
            tagRepository.save(t1);
            Tag t2 = tagRepository.findOne(t1.getId());

            assert t1.equals(t2);
            assert t1.getName().equals(t2.getName());

            // Set new values
            t1.setName("UPDATETAG");
            tagRepository.save(t1);

            t2 = tagRepository.findOne(t1.getId());

            // Assert
            assert t1.equals(t2);
            assert t1.getName().equals(t2.getName());
        } finally {
            // Cleanup
            tagRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }

    @Test
    public void deleteTag() {
        try {
            // Setup
            Player p1 = new Player("P1", "PASS1");
            playerRepository.save(p1);
            Tag t1 = new Tag(p1, "TESTTAG");
            tagRepository.save(t1);

            // Assert
            assert tagRepository.findOne(t1.getId()) != null;

            tagRepository.delete(t1.getId());

            //Assert
            assert tagRepository.findOne(t1.getId()) == null;
        } finally {
            // Cleanup
            tagRepository.deleteAll();
            playerRepository.deleteAll();
        }
    }
}
