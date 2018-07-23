package org.gamelog.repos;

import org.gamelog.model.Game;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface GameRepository extends Repository<Game, Long> {
    // Copied from CrudRepository
    @Async
    CompletableFuture<Game> save(Game game);

    @Async
    CompletableFuture<Iterable<Game>> save(Iterable<Game> game);

    @Async
    CompletableFuture<Game> findOne(Long id);

    @Async
    CompletableFuture<Iterable<Game>> findAll();

    @Async
    CompletableFuture<Void> delete(Long gameid);

    @Async
    CompletableFuture<Void> delete(Game game);

    @Async
    CompletableFuture<Void> deleteAll();

    @Async
    CompletableFuture<Boolean> exists(Long id);
}