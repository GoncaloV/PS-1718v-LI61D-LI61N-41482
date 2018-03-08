package pt.ps.gamelog.Model.Repos;

import org.springframework.data.repository.CrudRepository;
import pt.ps.gamelog.Model.Entities.Player;

// This will be AUTO IMPLEMENTED by Spring into a Bean called PlayerRepository
// CRUD refers Create, Read, Update, Delete

public interface PlayerRepository extends CrudRepository<Player, Long> {

}