package org.gamelog;

import org.gamelog.model.*;
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

    /**
     * This test covers the creating, finding and deleting of one tag.
     */
    @Test
    @Transactional
    public void testCreateFindDelete(){
        final String TAG_NAME = "TEST_TAG";
        final Tag CREATED_TAG = tagService.createTag(TAG_NAME);
        final Tag FOUND_TAG = tagService.findTag(TAG_NAME);
        assert CREATED_TAG == FOUND_TAG;

        tagService.delete(TAG_NAME);
        final Tag DELETED_TAG = tagService.findTag(TAG_NAME);
        assert DELETED_TAG == null;
    }
}
