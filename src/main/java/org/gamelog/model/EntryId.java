package org.gamelog.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntryId implements Serializable{
    @ManyToOne
    private Player player;
    @ManyToOne
    private Game game;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected EntryId() {}

    public EntryId(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this)
            return true;

        if (!(o instanceof Entry || o instanceof EntryId))
            return false;

        EntryId ei = o instanceof Entry ? ((Entry) o).getId() : (EntryId) o;
        return player.getId() == ei.player.getId() && game.getId() == ei.game.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player);
    }
}
