package org.gamelog.service;

import org.gamelog.model.Entry;
import org.gamelog.model.EntryId;
import org.gamelog.model.Game;
import org.gamelog.model.Player;
import org.gamelog.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class EntryService {
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    GameService gameService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Iterable<Entry> findAllEntriesForPlayerById(Player p){
        return entryRepository.findAllByIdPlayer(p);
    }

    public CompletableFuture<Entry> addEntryForPlayer(Integer rating, String review, boolean favorite, boolean secret, LocalDate date, long gameId, Player p){
        return gameService.findGameInfoById(gameId).thenApply(game -> {
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

    public Entry findByPlayerAndGame(Player player, Game game) {
        return entryRepository.findOne(new EntryId(player, game));
    }

    public Iterable<Entry> findPublicEntriesForGameById(Game game) {
        ArrayList<Entry> entries = (ArrayList<Entry>) entryRepository.findAllByIdGame(game);
        entries.forEach(entry -> {
            if(entry.isPrivate())
                entries.remove(entry);
        });
        return entries;
    }
}
