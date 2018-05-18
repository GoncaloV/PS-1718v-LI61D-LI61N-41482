package org.gamelog.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Gamelist {
    @EmbeddedId
    private GamelistId id;
    @ManyToMany
    private Set<Game> games = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    protected Gamelist() { }

    public Gamelist(Player id, String name) {
        this.id = new GamelistId(id, name);
    }

    public GamelistId getId() {
        return id;
    }

    public Set<Game> getGames() {
        return games;
    }
    public void removeGame(Game game) { games.remove(game); }
    public void addGame(Game game){
        games.add(game);
    }

    public Set<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }
}
