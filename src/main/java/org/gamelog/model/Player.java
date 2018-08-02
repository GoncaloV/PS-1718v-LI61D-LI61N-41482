package org.gamelog.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Player implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min=6, max=20)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(min=8, max=128)
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
    @Transient
    @Override
    public boolean isAccountNonExpired() { return true; }

    // Accounts are never locked, always return true.
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credentials are never expired, always return true;
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // All accounts are enabled, always return true;
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}