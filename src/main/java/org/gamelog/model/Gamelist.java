package org.gamelog.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Gamelist {
    @EmbeddedId
    private GamelistId id;
    private String name;
    @ManyToMany
    private List<Game> games;
    @ManyToMany
    private List<Tag> tags;

    protected Gamelist() { }

    public Gamelist(Player id, String name) {
        this.id = new GamelistId(id);
        this.name = name;
    }

    public GamelistId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }
    public void removeGame(Game game) { games.remove(game); }
    public void addGame(Game game){
        games.add(game);
    }
}
