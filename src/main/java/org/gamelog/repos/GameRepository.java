package org.gamelog.repos;

import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

}