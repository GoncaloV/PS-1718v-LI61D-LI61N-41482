package org.gamelog.service;

import org.gamelog.model.Game;
import org.gamelog.model.Gamelist;
import org.gamelog.model.Player;
import org.gamelog.model.Tag;
import org.gamelog.repos.GamelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class GamelistService {
    @Autowired
    private GamelistRepository gamelistRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private TagService tagService;

    /**
     * Gets all gamelists for one player.
     * @param player The player to whom the gamelists belong to.
     * @return An iterable of all gamelists that belong to a player.
     */
    public CompletableFuture<Iterable<Gamelist>> findAllByPlayerId(Player player){
        return gamelistRepository.findTop5ByIdPlayer(player);
    }

    /**
     * Finds the 5 first gamelists for one game.
     * @param game The game that the gamelists should contain.
     * @return An iterable of the 5 first gamelists for one game.
     */
    public CompletableFuture<Iterable<Gamelist>> findAllByGameId(Game game) {
        return gamelistRepository.findTop5ByGames(game);
    }

    /**
     * Finds a gamelist with a certain name, belonging to a certain player.
     * @param player The player instance to which the gamelist should belong to.
     * @param listname The name of the list.
     * @return A completableFuture for the gamelist with listname, belonging to player player.
     */
    public CompletableFuture<Gamelist> findOneByPlayerAndListName(Player player, String listname) {
        return gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
    }

    /**
     * Creates a new gamelist under a specified player with a specified name.
     * @param player The player who created the gamelist.
     * @param name The new gamelist's name.
     * @return A completable future containing the created gamelist, if created successfully, or null otherwise.
     */
    public CompletableFuture<Gamelist> addNewList(Player player, String name) {
        Gamelist gamelist = new Gamelist(player, name);
        return gamelistRepository.save(gamelist);
    }

    /**
     * TODO: Parallel async? Fix join.
     * @param player
     * @param listname
     * @param gameid
     * @return
     */
    public CompletableFuture<Gamelist> addGameToList(Player player, String listname, Long gameid) {
        CompletableFuture<Gamelist> gamelistCompletableFuture = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        CompletableFuture<Game> gameCompletableFuture = gameService.findGameById(gameid);
        return gamelistCompletableFuture.thenCombine(gameCompletableFuture, (gamelist, game) -> {
            gamelist.addGame(game);
            return gamelistRepository.save(gamelist).join();
        });
    }

    public CompletableFuture<Gamelist> deleteGameFromList(Player player, String listname, Long gameid) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname).join(); // TODO: fix join
        Game game = gameService.findGameById(gameid).join(); // TODO: fix join
        gamelist.removeGame(game);
        return gamelistRepository.save(gamelist);
    }

    public void deleteList(Player player, String listname) {
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname).join(); // TODO: fix join
        gamelistRepository.delete(gamelist.getId());
    }

    public CompletableFuture<Gamelist> addTagToList(Player player, String listname, String tagname) {
        Tag t = tagService.findTag(tagname).join(); // TODO: fix join
        t = t == null ? tagService.createTag(tagname).join() : t; // TODO: fix join
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname).join(); // TODO: fix join
        gamelist.addTag(t);
        return gamelistRepository.save(gamelist);
    }

    public CompletableFuture<Gamelist> addTagToList(Player player, Gamelist gamelist, Tag tag) {
        gamelist.addTag(tag);
        return gamelistRepository.save(gamelist);
    }

    public CompletableFuture<Gamelist> addTagsToList(Player player, Gamelist gamelist, Iterable<Tag> tags) {
        for (Tag t :
                tags) {
            gamelist.addTag(t);
        }
        return gamelistRepository.save(gamelist);
    }

    public CompletableFuture<Iterable<Gamelist>> removeTagFromLists(Set<Gamelist> gamelists, Tag tag) {
        gamelists.forEach(gamelist -> {
            gamelist.removeTag(tag);
        });
        return gamelistRepository.save(gamelists);
    }

    public CompletableFuture<Gamelist> removeTagFromlist(Player player, String listname, String tagname) {
        Tag t = tagService.findTag(tagname).join(); // TODO: fix join
        Gamelist gamelist = gamelistRepository.findOneByIdPlayerAndIdName(player, listname).join(); // TODO: fix join
        gamelist.removeTag(t);
        return gamelistRepository.save(gamelist);
    }

    public CompletableFuture<Gamelist> findOne(Gamelist gamelist) {
        return gamelistRepository.findOne(gamelist.getId());
    }
}
