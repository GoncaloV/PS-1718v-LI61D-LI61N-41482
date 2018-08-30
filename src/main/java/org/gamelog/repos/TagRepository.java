package org.gamelog.repos;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Tag;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface TagRepository extends Repository<Tag, String> {
    // Copied from CrudRepository
    @Async
    CompletableFuture<Tag> save(Tag tag);

    @Async
    CompletableFuture<Iterable<Tag>> save(Iterable<Tag> tags);

    @Async
    CompletableFuture<Tag> findOne(String name);

    @Async
    CompletableFuture<Iterable<Tag>> findAll();

    @Async
    CompletableFuture<Void> delete(Tag tag);

    @Async
    CompletableFuture<Void> delete(String name);

    @Async
    CompletableFuture<Void> deleteAll();

    // Custom queries
    CompletableFuture<Iterable<Tag>> findAllByGamelists(Gamelist gamelist);
}