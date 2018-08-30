package org.gamelog.model;

import org.hibernate.validator.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GamelistId implements Serializable{
    @ManyToOne
    @NotNull
    private Player player;

    @NotBlank
    @NotNull
    @Size(max=64)
    private String name;

    protected GamelistId() { }

    public GamelistId(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this)
            return true;

        if (!(o instanceof Gamelist || o instanceof GamelistId))
            return false;

        GamelistId gli = o instanceof Gamelist ? ((Gamelist) o).getId() : (GamelistId) o;
        return player.getId() == gli.player.getId() && name.equals(gli.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, player);
    }
}
