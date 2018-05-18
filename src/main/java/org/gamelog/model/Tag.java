package org.gamelog.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Tag {
    @EmbeddedId
    private TagId id;

    @ManyToMany(mappedBy = "tags")
    private Set<Gamelist> gamelists = new HashSet<>();

    protected Tag() {}

    public Tag(Player player, String name) {
        this.id = new TagId(player, name);
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Tag))
            return false;
        return getId().equals(((Tag) o).getId());
    }

    public TagId getId() {
        return id;
    }

    public Set<Gamelist> getGamelists() {
        return gamelists;
    }
}
