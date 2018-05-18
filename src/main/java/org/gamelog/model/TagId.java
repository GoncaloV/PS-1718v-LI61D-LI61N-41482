package org.gamelog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagId implements Serializable {
    @ManyToOne
    private Player player;
    private String name;

    protected TagId() {}

    public TagId(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Tag || o instanceof TagId))
            return false;

        TagId ti = o instanceof Tag ? ((Tag) o).getId() : (TagId) o;
        return player.getId() == ti.player.getId() && name.equals(ti.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
