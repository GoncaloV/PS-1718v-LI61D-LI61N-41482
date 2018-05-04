package org.gamelog.model;

import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Game {
    @Id
    private long id;

    @Formula("(SELECT COUNT(*) FROM entry e WHERE e.game_id = id AND e.is_favorite)")
    private long favorites;

    @Formula("(SELECT AVG(e.rating) FROM entry e WHERE e.game_id = id)")
    private Float averageRatings;

    @Formula("(SELECT COUNT(*) FROM entry e WHERE e.game_id = id)")
    private int plays;

    // Obtained from API
    private String name;
    @Column(columnDefinition = "TEXT")// Summaries tend to be very long.
    private String summary;
    private String coverUrl;

    protected Game() {}

    public Game(long id) {
        this.id = id;
    }

    public Game(long id, String name, String coverUrl) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;
    }

    public long getId() {
        return id;
    }

    public long getFavorites() {
        return favorites;
    }

    public Float getAverageRatings() {
        return averageRatings;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public int getPlays() {
        return plays;
    }
}
