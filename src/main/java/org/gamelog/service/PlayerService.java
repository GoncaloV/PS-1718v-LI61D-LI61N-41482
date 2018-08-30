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
        return playerRepository.findPlayerByNameIgnoreCase(name);
    }

    @Async
    public CompletableFuture<Void> deleteAll() {
        return playerRepository.deleteAll();
    }

    public CompletableFuture<Void> delete(Player p){
        return playerRepository.delete(p);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String usernameUpperCase = username.toUpperCase();
        Player player = findByName(usernameUpperCase).join();
        if (player != null) return player;
        else throw new UsernameNotFoundException(username);
    }

    /**
     * Registers a new player in the database. First checks if a player under the desired name is already in the database.
     * @param name The desired player name.
     * @param password The desired player password.
     * @return A completable future containing: Null, if a player under that name already exists; Another completable future containing the player created, if it was created successfully.
     */
    @Async
    public CompletableFuture<Player> createPlayer(String name, String password) {
        if(!isAlphanumeric(name)){
            try {
                throw new IllegalArgumentException("Player name not alphanumeric.");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return playerRepository.findPlayerByNameIgnoreCase(name)
                .thenCompose(player -> {
            if (player != null)
                throw new IllegalArgumentException("Username is taken.");
            else {
                return playerRepository.save(new Player(name, password));
            }
        });
    }

    /**
     * Finds all players in the database.
     * @return A completable future containing an iterable with all the players found.
     */
    @Async
    public CompletableFuture<Iterable<Player>> findAll() {
        return playerRepository.findAll();
    }

    public boolean isAlphanumeric(String s) {
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }
}
