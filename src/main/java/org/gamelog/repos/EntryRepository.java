package org.gamelog.repos;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.gamelog.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntryRepository extends CrudRepository<Entry, EntryId>{
    Iterable<Entry> findAllByIdPlayer(Player player);
}
