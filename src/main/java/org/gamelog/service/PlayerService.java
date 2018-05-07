package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PlayerService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player save(Player p) {
        return playerRepository.save(p);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = findByName(username);
        if (player != null) return player;
        else throw new UsernameNotFoundException(username);
    }
}
