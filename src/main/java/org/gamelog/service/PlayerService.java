package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    private InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    public void save(Player p) {
        playerRepository.save(p);
        inMemoryUserDetailsManager.createUser(User.withUsername(p.getName()).password(p.getPassword()).roles("USER").build());
    }

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(long id){
        return playerRepository.findOne(id);
    }

    public Player findByName(String name){
        return playerRepository.findPlayerByName(name);
    }

    public void deleteAll() {
        playerRepository.deleteAll();
    }

    public void delete(Player p){
        playerRepository.delete(p);
    }

    public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
        return inMemoryUserDetailsManager;
    }

    public void loadAll() {
        for (Player p : findAll()) {
            inMemoryUserDetailsManager.createUser(User.withUsername(p.getName()).password(p.getPassword()).roles("USER").build());
        }
    }
}
