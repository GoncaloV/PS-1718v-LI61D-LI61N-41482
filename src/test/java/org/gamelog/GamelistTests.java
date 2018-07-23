package org.gamelog;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.model.Tag;
import org.gamelog.service.GamelistService;
import org.gamelog.service.PlayerService;
import org.gamelog.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class GamelistTests {
    @Autowired
    private GamelistService gamelistService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PlayerService playerService;

    final String TAG_NAME = "TEST_TAG_NAME";
    final String PLAYER_NAME = "TEST_PLAYER_NAME";
    final String PLAYER_PASSWORD = "TEST_PASSWORD";
    final String GAMELIST_NAME = "TEST_GAMELIST_NAME";

    /**
     * This test covers the addition of a new tag to a list. The tag should be created and added to the list.
     */
    @Test
    @Transactional
    public void testAddNewTagToList(){
        final Player PLAYER = playerService.createPlayer(PLAYER_NAME, PLAYER_PASSWORD).join(); //TODO: Fix tests? Remove all joins.
        final Gamelist GAMELIST = gamelistService.addNewList(PLAYER, GAMELIST_NAME).join();
        gamelistService.addTagToList(PLAYER, GAMELIST_NAME, TAG_NAME);

        final Gamelist GAMELIST_FOUND = gamelistService.findOneByPlayerAndListName(PLAYER, GAMELIST_NAME).join();
        final Tag TAG_CREATED = tagService.findTag(TAG_NAME).join();
        assert GAMELIST_FOUND.getTags().contains(TAG_CREATED);
    }

    /**
     * This test covers the addition of an existing tag to a list. The tag should be added to the list.
     */
    @Test
    @Transactional
    public void testAddExistingTagToList(){
        final Player PLAYER = playerService.createPlayer(PLAYER_NAME, PLAYER_PASSWORD).join();
        final Gamelist GAMELIST = gamelistService.addNewList(PLAYER, GAMELIST_NAME).join();
        final Tag TAG_CREATED = tagService.createTag(TAG_NAME).join();
        gamelistService.addTagToList(PLAYER, GAMELIST_NAME, TAG_NAME);

        final Gamelist GAMELIST_FOUND = gamelistService.findOneByPlayerAndListName(PLAYER, GAMELIST_NAME).join();
        assert GAMELIST_FOUND.getTags().contains(TAG_CREATED);
    }
}
