package org.gamelog.repos;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.repository.Repository;
import org.gamelog.model.Game;
import org.gamelog.model.GamelistId;
import org.gamelog.model.Player;
import org.gamelog.model.Gamelist;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface GamelistRepository extends Repository<Gamelist, GamelistId> {
    // Copied from CrudRepository
    @Async
    CompletableFuture<Gamelist> save(Gamelist gamelist);

    @Async
    CompletableFuture<Iterable<Gamelist>> save(Iterable<Gamelist> gamelists);

    @Async
    CompletableFuture<Gamelist> findOne(GamelistId gamelistId);

    @Async
    CompletableFuture<Iterable<Gamelist>> findAll();

    @Async
    CompletableFuture<Void> delete(GamelistId gamelistId);

    @Async
    CompletableFuture<Void> delete(Gamelist gamelist);

    @Async
    CompletableFuture<Void> deleteAll();

    @Async
    CompletableFuture<Boolean> exists(GamelistId gamelistId);

    // Custom queries
    @Async
    CompletableFuture<Iterable<Gamelist>> findTop5ByIdPlayer(Player player);

    @Async
    CompletableFuture<Iterable<Gamelist>> findTop5ByGames(Game game);

    @Async
    CompletableFuture<Gamelist> findOneByIdPlayerAndIdName(Player player, String gamelistname);
}