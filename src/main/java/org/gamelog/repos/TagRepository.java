package org.gamelog.repos;

import org.springframework.data.repository.CrudRepository;
import org.gamelog.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

}