package org.gamelog.repos;

import org.gamelog.model.Player;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface PlayerRepository extends Repository<Player, Long> {
    @Async
    CompletableFuture<Player> save(Player player);

    @Async
    CompletableFuture<Iterable<Player>> save(Iterable<Player> entities);

    @Async
    CompletableFuture<Player> findOne(Long id);

    @Async
    CompletableFuture<Void> delete(Player player);

    @Async
    CompletableFuture<Void> deleteAll();

    @Async
    CompletableFuture<Player> findPlayerByName(String name);

    @Async
    CompletableFuture<Iterable<Player>> findAll();
}