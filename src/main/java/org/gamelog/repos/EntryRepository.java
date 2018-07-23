package org.gamelog.repos;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

public interface EntryRepository extends Repository<Entry, EntryId> {
    // Copied from CrudRepository
    @Async
    CompletableFuture<Entry> save(Entry entry);

    @Async
    CompletableFuture<Iterable<Entry>> save(Iterable<Entry> entries);

    @Async
    CompletableFuture<Entry> findOne(EntryId entryId);

    @Async
    CompletableFuture<Iterable<Entry>> findAll();

    @Async
    CompletableFuture<Void> delete(EntryId entryid);

    @Async
    CompletableFuture<Void> delete(Entry entry);

    @Async
    CompletableFuture<Void> deleteAll();

    @Async
    CompletableFuture<Boolean> exists(EntryId id);

    // Custom queries
    @Async
    CompletableFuture<Iterable<Entry>> findAllByIdPlayer(Player player);

    @Async
    CompletableFuture<Iterable<Entry>> findAllByIdGame(Game game);

    @Async
    CompletableFuture<Entry> findOneByIdPlayerAndIdGame(Player player, Game game);
}
