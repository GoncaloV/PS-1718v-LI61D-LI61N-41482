package org.gamelog.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Player;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    @Async
    @Query(value = "insert into player (id,name,password) VALUES (:id,:name,:password)", nativeQuery = true)
    CompletableFuture<Player> createPlayer(@Param("id") Long id, @Param("name") String name, @Param("password") String password);

    Player findPlayerByName(@Param("name") String name);

    @Async
    CompletableFuture<Player> findOneById(long id);
}