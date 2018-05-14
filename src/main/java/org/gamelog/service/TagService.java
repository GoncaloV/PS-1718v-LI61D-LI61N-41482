package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.model.Tag;
import org.gamelog.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Iterable<Tag> findAllTagsByPlayer(Player p){
        return tagRepository.findAllByIdPlayer(p);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteAll() {
        tagRepository.deleteAll();
    }

    public Tag addTagToPlayer(Player p, String tagname) {
        Tag t = new Tag(p, tagname);
        return tagRepository.save(t);
    }

    public void delete(Player p, Long tagid) {
        Tag tag = tagRepository.findOneByIdPlayerAndIdId(p, tagid);
        tagRepository.delete(tag.getId());
    }
}
