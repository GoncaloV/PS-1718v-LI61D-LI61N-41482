package org.gamelog.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Entry implements Serializable{
    @EmbeddedId
    private EntryId id;

    @Column(nullable = false)
    private boolean isPrivate = true;

    private Integer rating;

    private String review;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags = new LinkedList<>();

    @Column(nullable = false)
    private boolean isFavorite = false;

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
        if(rating >= 0 && rating <= 10)
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

    public List<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Entry))
            return false;
        return getId().equals(((Entry) o).getId());
    }
}
