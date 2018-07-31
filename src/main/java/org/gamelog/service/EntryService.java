package org.gamelog.service;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class EntryService {
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    GameService gameService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Finds all entries for one player.
     * @param p The player that the entries should belong to.
     * @return Entries found for player p.
     */
    @Async
    public CompletableFuture<Iterable<Entry>> findAllEntriesForPlayerById(Player p){
        return entryRepository.findAllByIdPlayer(p);
    }

    /** TODO: Multiple entries per game?
     * Adds a new entry for a game for an existing player.
     * @param rating A score between 1 and 10 given to the game by the player.
     * @param review A text written about the game by the player.
     * @param favorite Whether the game is a player's favorite or not.
     * @param secret Whether this information will be accessible by other users or not.
     * @param date The date that the player decided to write the entry.
     * @param gameId The game's id.
     * @param player The player.
     * @return
     */
    @Async
    public CompletableFuture<Entry> saveEntry(Integer rating, String review, boolean favorite, boolean secret, LocalDate date, long gameId, Player player){
        return gameService.findGameInfoById(gameId).thenCompose(game -> {
            return entryRepository.findOneByIdPlayerAndIdGame(player, game).thenCompose(entry -> {
                Entry e = entry == null ? new Entry(player, game) : entry;
                    e.setRating(rating);
                    e.setReview(review);
                    e.setFavorite(favorite);
                    e.setPrivate(secret);
                    e.setDate(date);
                    return entryRepository.save(e);
            });
        });
    }

    /**
     * Finds all entries in the database.
     * @return All the entries in the database.
     */
    @Async
    public CompletableFuture<Iterable<Entry>> findAll() {
        return entryRepository.findAll();
    }

    /** TODO: Needs to be edited if multiple entries per game are allowed.
     * Finds the entry a player made for a game.
     * @param player The player who wrote the entry.
     * @param game The game the entry was written about.
     * @return An instance of Entry.
     */
    @Async
    public CompletableFuture<Entry> findByPlayerAndGame(Player player, Game game) {
        return entryRepository.findOne(new EntryId(player, game));
    }

    /**
     * Gets all non-private entries for a game.
     * @param game Game to find entries about.
     * @return All non-private entries for a game.
     */
    @Async
    public CompletableFuture<Iterable<Entry>> findPublicEntriesForGameById(Game game) {
        return entryRepository.findAllByIdGame(game).thenApply(entries -> {
            ArrayList<Entry> publicEntries = new ArrayList<>();
            entries.forEach(entry -> {
                if(!entry.isPrivate())
                    publicEntries.add(entry);
            });
            return publicEntries;
        });
    }

    @Async
    public CompletableFuture<Boolean> exists(Entry e) {
        return entryRepository.exists(e.getId());
    }

    @Async
    public CompletableFuture<Entry> findOne(Entry e) {
        return entryRepository.findOne(e.getId());
    }

}
