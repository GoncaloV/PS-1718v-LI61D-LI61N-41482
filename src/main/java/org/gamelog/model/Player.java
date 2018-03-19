package org.gamelog.model;

import javax.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(unique=true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    protected Player(){}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}