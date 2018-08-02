package org.gamelog.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Tag {
    @Id
    @NotBlank
    @Size(max=20)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Gamelist> gamelists = new HashSet();

    protected Tag() {}

    public Tag(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Set<Gamelist> getGamelists() {
        return gamelists;
    }
}
