package org.gamelog;

import org.gamelog.model.Entry;
import org.gamelog.model.Player;
import org.gamelog.repos.EntryRepository;
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

    @Test
    public void createEntry(){
        // Setup
        // Assert
        // Cleanup
    }

}
