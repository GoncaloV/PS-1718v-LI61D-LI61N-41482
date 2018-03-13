package org.gamelog.model;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GamelistId implements Serializable{
    @ManyToOne
    private Player player;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected GamelistId() { }

    public GamelistId(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this)
            return true;

        if (!(o instanceof Gamelist || o instanceof GamelistId))
            return false;

        GamelistId gli = o instanceof Gamelist ? ((Gamelist) o).getId() : (GamelistId) o;
        return player.getId() == gli.player.getId() && id == gli.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player);
    }
}
