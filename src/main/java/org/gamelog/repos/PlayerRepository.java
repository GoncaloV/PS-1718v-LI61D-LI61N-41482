package org.gamelog.repos;

import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Player;

// This will be AUTO IMPLEMENTED by Spring into a Bean called PlayerRepository
// CRUD refers Create, Read, Update, Delete

public interface PlayerRepository extends CrudRepository<Player, Long> {

}