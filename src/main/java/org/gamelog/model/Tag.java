package org.gamelog.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.*;

@Entity
public class Tag {
    @Id
    @NotBlank
    private String name;

    protected Tag() {}

    public Tag(String name) { this.name = name; }

    public String getName() {
        return name;
    }
}
