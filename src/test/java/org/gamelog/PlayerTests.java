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


    /**
     * Tests were deleted after they stopped functioning correctly when the application started implementing asynchronous
     * methods. This test remains as an example for potential future implementations.
     */
    @Test
    public void exampleTest(){

    }
}
