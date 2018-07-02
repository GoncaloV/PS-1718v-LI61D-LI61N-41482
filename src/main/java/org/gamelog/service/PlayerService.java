package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class PlayerService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player createPlayer(String name, String password) {
        Player p = new Player(name, password);
        return playerRepository.save(p);
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

    @Async
    public CompletableFuture<Player> createPlayer_async(String name, String password) {
        Player p = new Player(name, password);
        return playerRepository.createPlayer(p.getId(), name, password);
    }

    @Async
    public CompletableFuture<Player> findById_async(long id) {
        return playerRepository.findOneById(id);
    }
}
