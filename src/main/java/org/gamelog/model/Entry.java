package org.gamelog.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Entry {
    @EmbeddedId
    private EntryId id;

    @Column(nullable = false)
    private boolean isPrivate = true;

    private Integer rating;

    @Size(max=512)
    private String review;

    @ManyToMany
    @OrderBy("name")
    private Set<Tag> tags = new LinkedHashSet<>();

    @Column(nullable = false)
    private boolean isFavorite = false;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    public Entry(Player playerId, Game gameId) {
        this.id = new EntryId(playerId, gameId);
    }

    protected Entry(){}

    public EntryId getId() {
        return id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if(rating == null) {
            this.rating = null;
        } else
        if(rating >= 1 && rating <= 10)
            this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Set<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }
}
