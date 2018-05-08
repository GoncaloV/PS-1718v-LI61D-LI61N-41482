package org.gamelog.repos;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface EntryRepository extends CrudRepository<Entry, EntryId>{
    Iterable<Entry> findAllByIdPlayer(Player player);
    Iterable<Entry> findAllByIdGame(Game game);
}
