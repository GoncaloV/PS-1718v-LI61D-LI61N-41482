package org.gamelog.repos;

import org.gamelog.model.Game;
import org.gamelog.model.GamelistId;
import org.gamelog.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Gamelist;

public interface GamelistRepository extends CrudRepository<Gamelist, GamelistId> {
    Iterable<Gamelist> findTop5ByIdPlayer(Player player);
    Iterable<Gamelist> findTop5ByGames(Game game);
    Gamelist findOneByIdPlayerAndIdId(Player player, Long listid);
}