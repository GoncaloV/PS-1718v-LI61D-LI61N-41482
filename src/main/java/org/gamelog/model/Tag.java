package org.gamelog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//TODO
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;
}
