package org.gamelog.service;

import org.gamelog.model.Entry;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
public class EntryService {
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    GameService gameService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Iterable<Entry> findAllEntriesForPlayerById(Player p){
        Iterable<Entry> entries = entryRepository.findAllByIdPlayer(p);
        return entries;
    }

    public CompletableFuture<Entry> addEntryForPlayer(Integer rating, String review, boolean favorite, boolean secret, LocalDate date, long gameId, Player p){
        return gameService.getGameInfoById(gameId).thenApply(game -> {
            Entry e = new Entry(p, game);
            e.setRating(rating);
            e.setReview(review);
            e.setFavorite(favorite);
            e.setPrivate(secret);
            e.setDate(date);
            return entryRepository.save(e);
        });
    }

    public Iterable<Entry> findAll() {
        return entryRepository.findAll();
    }
}
