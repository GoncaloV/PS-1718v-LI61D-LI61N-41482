package org.gamelog.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Player;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called PlayerRepository
// CRUD refers Create, Read, Update, Delete

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findPlayerByName(@Param("name") String name);
}