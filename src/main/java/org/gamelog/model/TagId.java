package org.gamelog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagId implements Serializable {
    @ManyToOne
    private Player player;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    protected TagId() {}

    public TagId(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Tag || o instanceof TagId))
            return false;

        TagId ti = o instanceof Tag ? ((Tag) o).getId() : (TagId) o;
        return player.getId() == ti.player.getId() && id == ti.getId();

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player);
    }
}
