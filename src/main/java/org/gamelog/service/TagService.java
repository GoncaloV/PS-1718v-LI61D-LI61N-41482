package org.gamelog.service;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.model.Tag;
import org.gamelog.repos.GamelistRepository;
import org.gamelog.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    /**
     * Creates a tag and stores it in the database.
     * @param tagname The name of the tag to be created.
     * @return The tag created.
     */
    public Tag createTag(String tagname) {
        Tag t = new Tag(tagname);
        return tagRepository.save(t);
    }

    /**
     * Finds a tag in the database.
     * @param tagname Name of tag to be found.
     * @return The tag found.
     */
    public Tag findTag(String tagname){
        return tagRepository.findOne(tagname);
    }

    /**
     * Deletes a tag from the database.
     * @param tagname Name of tag to be deleted.
     */
    public void deleteTag(String tagname) {
        tagRepository.delete(tagname);
    }

    /**
     * Finds all tags in the database.
     * @return An Iterable containing all the tags in the database.
     */
    public Iterable<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Iterable<Tag> findAllTagsForGamelist(Gamelist gamelist){
        return tagRepository.findAllByGamelists(gamelist);
    }
}
