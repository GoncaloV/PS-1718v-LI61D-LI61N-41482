package org.gamelog.repos;

import org.gamelog.model.Gamelist;
import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Tag;

public interface TagRepository extends CrudRepository<Tag, String> {
    Iterable<Tag> findAllByGamelists(Gamelist gamelist);
}