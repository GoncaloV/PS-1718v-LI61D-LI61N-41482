package org.gamelog.repos;

import org.gamelog.model.Player;
import org.gamelog.model.TagId;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Tag;

public interface TagRepository extends CrudRepository<Tag, TagId> {
    Iterable<Tag> findAllByIdPlayer(Player p);
    void deleteOneByIdPlayerAndIdName(Player p, String tagname);
    Tag findOneByIdPlayerAndIdName(Player p, String tagname);
}