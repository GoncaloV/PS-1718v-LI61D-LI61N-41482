package org.gamelog.model;

import javax.persistence.*;

@Entity
public class Tag {
    @EmbeddedId
    private TagId id;

    @Column(unique=true, nullable = false)
    private String name;

    protected Tag() {}

    public Tag(Player player, String name) {
        this.id = new TagId(player);
        this.name = name;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
