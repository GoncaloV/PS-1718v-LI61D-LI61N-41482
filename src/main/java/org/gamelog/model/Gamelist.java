package org.gamelog.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Gamelist {
    @EmbeddedId
    private GamelistId id;
    @ManyToMany
    @OrderBy("id")
    private Set<Game> games = new LinkedHashSet<>();
    @ManyToMany
    @OrderBy("name")
    private Set<Tag> tags = new LinkedHashSet<>();

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
        if(!games.contains(game))
            games.add(game);
    }

    public Set<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }

    @Transient
    public Iterable<Game> getFirst10Games() {
        return () -> getGames().stream().limit(10).iterator();
    }

    @Transient
    public boolean hasGame(Game game){
        return games.contains(game);
    }

    @Transient
    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }
}
