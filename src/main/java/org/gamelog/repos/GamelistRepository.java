package org.gamelog.repos;

import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Gamelist;

public interface GamelistRepository extends CrudRepository<Gamelist, Long> {

}