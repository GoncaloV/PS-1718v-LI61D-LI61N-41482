package org.gamelog.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Gamelist {
    @EmbeddedId
    private GamelistId id;
    private String name;
    @ManyToMany
    private List<Game> games = new LinkedList<>();
    @ManyToMany
    private List<Tag> tags = new LinkedList<>();

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

    public List<Tag> getTags() { return tags; }
    public void addTag(Tag t){ tags.add(t); }
    public void removeTag(Tag t){ tags.remove(t); }
}
