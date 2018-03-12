package org.gamelog.repos;

import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Entry;

public interface EntryRepository extends CrudRepository<Entry, Long>{
}
