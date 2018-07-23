package org.gamelog.service;

import org.gamelog.model.Gamelist;
import org.gamelog.model.Tag;
import org.gamelog.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    /**
     * Creates a tag and stores it in the database.
     * @param tagname The name of the tag to be created.
     * @return The tag created.
     */
    public CompletableFuture<Tag> createTag(String tagname) {
        return findTag(tagname).thenCompose(tag -> tag == null ? tagRepository.save(new Tag(tagname)) : null);
    }

    /**
     * Finds a tag in the database.
     * @param tagname Name of tag to be found.
     * @return The tag found.
     */
    public CompletableFuture<Tag> findTag(String tagname){
        return tagRepository.findOne(tagname);
    }

    /**
     * Deletes a tag from the database.
     * @param tagname Name of tag to be deleted.
     */
    public CompletableFuture<Void> deleteTag(String tagname) {
        return tagRepository.delete(tagname);
    } // TODO: Completable future void?

    /**
     * Finds all tags in the database.
     * @return An Iterable containing all the tags in the database.
     */
    public CompletableFuture<Iterable<Tag>> getAllTags() {
        return tagRepository.findAll();
    }

    public CompletableFuture<Iterable<Tag>> findAllTagsForGamelist(Gamelist gamelist){
        return tagRepository.findAllByGamelists(gamelist);
    }
}
