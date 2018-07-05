package org.gamelog;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.model.Tag;
import org.gamelog.service.GamelistService;
import org.gamelog.service.PlayerService;
import org.gamelog.service.TagService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class TagTests {
    @Autowired
    private TagService tagService;

    @Autowired
    private GamelistService gamelistService;

    @Autowired
    private PlayerService playerService;

    final String TAG_NAME = "TEST_TAG_NAME";
    final String TAG_NAME_2 = "TEST_TAG_NAME_2";
    final String TAG_NAME_3 = "TEST_TAG_NAME_3";
    final String PLAYER_NAME = "TEST_PLAYER_NAME";
    final String PLAYER_PASSWORD = "TEST_PASSWORD";
    final String GAMELIST_NAME = "TEST_GAMELIST_NAME";
    /**
     * This test covers the creating, finding and deleting of one tag.
     */
    @Test
    @Transactional
    public void testCreateFindDelete(){
        final Tag CREATED_TAG = tagService.createTag(TAG_NAME);
        final Tag FOUND_TAG = tagService.findTag(TAG_NAME);
        assert CREATED_TAG == FOUND_TAG;

        tagService.deleteTag(TAG_NAME);
        final Tag DELETED_TAG = tagService.findTag(TAG_NAME);
        assert DELETED_TAG == null;
    }

/*    *//**
     * This test covers finding all tags for a certain gamelist. The tags must be in the same order.
     *//*
    @Test
    @Transactional
    public void testFindAllTagsForGamelist() {
        Player player = playerService.createPlayer(PLAYER_NAME, PLAYER_PASSWORD);
        Gamelist gamelist = gamelistService.addNewList(player, GAMELIST_NAME);
        Tag tag = tagService.createTag(TAG_NAME);
        Tag tag2 = tagService.createTag(TAG_NAME_2);
        Tag tag3 = tagService.createTag(TAG_NAME_3);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        tags.add(tag3);
        gamelistService.addTagsToList(player, gamelist, tags);
        ArrayList<Tag> tags2 = (ArrayList<Tag>) tagService.findAllTagsForGamelist(gamelist);
        for (int i = 0; i < tags.size(); i++) {
            assertEquals(tags.get(i), tags2.get(i));
        }
    }*/
}
