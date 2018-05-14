package org.gamelog;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Tag;
import org.gamelog.repos.PlayerRepository;
import org.gamelog.repos.TagRepository;
import org.gamelog.service.PlayerService;
import org.gamelog.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.gamelog.model.Player;

import java.util.ArrayList;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class TagTests {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private PlayerService playerService;

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

    @Test
    public void findAllTagsByPlayer_Test(){
        Player p1 = new Player("TESTNAME1", "TESTPASSWORD1");
        Player p2 = new Player("TESTNAME2", "TESTPASSWORD2");
        playerService.save(p1);
        playerService.save(p2);

        Tag t1 = new Tag(p1, "TESTTAG1");
        Tag t2 = new Tag(p1, "TESTTAG2");
        Tag t3 = new Tag(p2, "TESTTAG3");
        tagService.save(t1);
        tagService.save(t2);
        tagService.save(t3);

        ArrayList<Tag> tags = (ArrayList<Tag>) tagService.findAllTagsByPlayer(p1);
        assert tags.size() == 2;
        assert tags.get(0).getName().equals(t1.getName());
        assert tags.get(1).getName().equals(t2.getName());

        tagService.deleteAll();
        playerService.deleteAll();
    }
}
