package org.gamelog.model;

import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    private long id;

    @Formula("(SELECT COUNT(*) FROM entry e WHERE e.game_id = id AND e.is_favorite)")
    private long favorites;

    @Formula("(SELECT AVG(e.rating) FROM entry e WHERE e.game_id = id)")
    private Float averageRatings;

    protected Game() {}

    public Game(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getFavorites() {
        return favorites;
    }

    public float getAverageRatings() {
        return averageRatings;
    }
}
