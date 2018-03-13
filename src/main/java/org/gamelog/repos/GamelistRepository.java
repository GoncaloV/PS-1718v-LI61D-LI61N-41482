package org.gamelog.repos;

import org.gamelog.model.GamelistId;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Gamelist;

public interface GamelistRepository extends CrudRepository<Gamelist, GamelistId> {

}