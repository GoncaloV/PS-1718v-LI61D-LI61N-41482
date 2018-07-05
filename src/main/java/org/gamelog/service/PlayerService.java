package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PlayerService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    @Async
    public CompletableFuture<Player> findById(long id){
        return playerRepository.findOne(id);
    }

    @Async
    public CompletableFuture<Player> findByName(String name){
        return playerRepository.findPlayerByName(name);
    }

    @Async
    public void deleteAll() {
        playerRepository.deleteAll();
    }

    public void delete(Player p){
        playerRepository.delete(p);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = findByName(username).join();
        if (player != null) return player;
        else throw new UsernameNotFoundException(username);
    }

    @Async
    public CompletableFuture<Player> createPlayer(String name, String password) {
        Player p = new Player(name, password);
        return playerRepository.save(p);
    }

    @Async
    public CompletableFuture<Iterable<Player>> findAll() {
        return playerRepository.findAll();
    }
}
