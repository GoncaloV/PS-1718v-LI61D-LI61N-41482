package org.gamelog.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @NotBlank
    @Size(min=1, max=20)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @OrderBy("id.name")
    private Set<Gamelist> gamelists = new LinkedHashSet<>();

    protected Tag() {}

    public Tag(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Set<Gamelist> getGamelists() {
        return gamelists;
    }

    public boolean isAlphanumeric2(String name) {
        for (int i=0; i<name.length(); i++) {
            char c = name.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }
}
