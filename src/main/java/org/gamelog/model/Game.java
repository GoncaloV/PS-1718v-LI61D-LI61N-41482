package org.gamelog.model;

import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    private long id;

    protected Game() {}

    public Game(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
