package org.gamelog.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Gamelist {
    @EmbeddedId
    private GamelistId id;
    @ManyToMany
    private List<Game> games = new ArrayList<>(); //TODO: Change all collections to linked hash sets?
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    protected Gamelist() { }

    public Gamelist(Player id, String name) {
        this.id = new GamelistId(id, name);
    }

    public GamelistId getId() {
        return id;
    }

    public List<Game> getGames() {
        return games;
    }
    public void removeGame(Game game) { games.remove(game); }
    public void addGame(Game game){
        if(!games.contains(game))
            games.add(game);
    }

    public List<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }

    @Transient
    public List<Game> getFirst10Games() {
        return getGames().stream().limit(10).collect(Collectors.toList());
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
