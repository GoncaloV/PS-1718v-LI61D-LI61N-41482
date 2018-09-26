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
    public CompletableFuture<Tag> createTag(String tagname) throws Exception {
        if(!isAlphanumeric(tagname)) throw new Exception("Tag name not alphanumeric.");
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
    }

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

    public boolean isAlphanumeric(String s) {
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }

    public CompletableFuture<Tag> tryFindTag(String tagname) {
        return findTag(tagname).thenCompose(tag -> {
            if (tag != null) return CompletableFuture.completedFuture(tag);
            else {
                try {
                    return createTag(tagname).thenApply(tag1 -> {
                        return tag1;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return CompletableFuture.completedFuture(tag);
            }
        });
    }
}
