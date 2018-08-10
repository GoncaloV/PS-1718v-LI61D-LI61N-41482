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
        return playerRepository.findPlayerByNameIgnoreCase(name)
                .thenCompose(player -> {
            if (player != null)
                return CompletableFuture.completedFuture(null);
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
}
