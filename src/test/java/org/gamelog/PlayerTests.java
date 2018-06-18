package org.gamelog;

import org.gamelog.service.PlayerService;
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
public class PlayerTests {
    @Autowired
    private PlayerService playerService;

    private final String NAME = "N", PASSWORD = "P", UPDATED_NAME = "N2", UPDATED_PASSWORD = "P2";
}
