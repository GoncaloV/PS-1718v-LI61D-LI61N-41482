package org.gamelog.repos;

import org.gamelog.model.Player;
import org.gamelog.model.TagId;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Tag;

public interface TagRepository extends CrudRepository<Tag, TagId> {
    Iterable<Tag> findAllByIdPlayer(Player p);
    void deleteOneByIdPlayerAndIdId(Player p, Long tagid);
    Tag findOneByIdPlayerAndIdId(Player p, Long tagid);
}