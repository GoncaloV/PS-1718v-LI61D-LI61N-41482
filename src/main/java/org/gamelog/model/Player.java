package org.gamelog.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Player implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    protected Player() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    public void setName(String name) { this.name = name; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "USER");
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    // Accounts are never expired, always return true.
    @Override
    public boolean isAccountNonExpired() { return true; }

    // Accounts are never locked, always return true.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credentials are never expired, always return true;
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // All accounts are enabled, always return true;
    @Override
    public boolean isEnabled() {
        return true;
    }
}