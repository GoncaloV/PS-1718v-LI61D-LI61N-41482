package org.gamelog.repos;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, EntryId>{
}
