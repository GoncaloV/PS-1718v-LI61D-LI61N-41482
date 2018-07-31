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
        return gamelistRepository.findOneByIdPlayerAndIdName(player, name).thenCompose(gamelist -> {
            if (gamelist == null)
                return gamelistRepository.save(new Gamelist(player, name));
            else {
                return CompletableFuture.completedFuture(null);
            }
        });
    }

    /**
     * Adds a new game to an existing gamelist.
     * @param player The player to whom the list belongs to.
     * @param listname The name of the list being edited.
     * @param gameid The game being added.
     * @return The gamelist with the new game.
     */
    public CompletableFuture<Gamelist> addGameToList(Player player, String listname, Long gameid) {
        CompletableFuture<Gamelist> gamelistCompletableFuture = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        CompletableFuture<Game> gameCompletableFuture = gameService.findGameById(gameid);
        CompletableFuture[] completableFutures = {gamelistCompletableFuture, gameCompletableFuture};
        return CompletableFuture.allOf(completableFutures).thenCompose(x -> {
            Gamelist gamelist = gamelistCompletableFuture.join();
            gamelist.addGame(gameCompletableFuture.join());
            return gamelistRepository.save(gamelist);
        });
    }

    /**
     * Deletes a game from a gamelist.
     * @param player The player to whom the list belongs to.
     * @param listname The name of the list being modified.
     * @param gameid The game being removed.
     * @return The modified gamelist.
     */
    public CompletableFuture<Gamelist> deleteGameFromList(Player player, String listname, Long gameid) {
        CompletableFuture<Gamelist> gamelistCompletableFuture = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        CompletableFuture<Game> gameCompletableFuture = gameService.findGameById(gameid);
        CompletableFuture[] completableFutures = {gamelistCompletableFuture, gameCompletableFuture};
        return CompletableFuture.allOf(completableFutures).thenCompose(x -> {
            Gamelist gamelist = gamelistCompletableFuture.join();
            gamelist.removeGame(gameCompletableFuture.join());
            return gamelistRepository.save(gamelist);
        });
    }

    /**
     * Deletes an existing gamelist.
     * @param player The player to whom the list belongs.
     * @param listname The name of the list being deleted.
     */
    public CompletableFuture<Void> deleteList(Player player, String listname) {
        return gamelistRepository.findOneByIdPlayerAndIdName(player, listname).thenCompose(gamelist -> gamelistRepository.delete(gamelist.getId()));
    }

    /**
     * Adds a tag to a gamelist.
     * @param player The player to whom the gamelist belongs to.
     * @param listname The name of the list being modified.
     * @param tagname The name of the tag being added.
     * @return The modified gamelist.
     */
    public CompletableFuture<Gamelist> addTagToList(Player player, String listname, String tagname) {
        CompletableFuture<Tag> tagCompletableFuture = tagService.findTag(tagname).
                thenCompose(tag -> tag == null ? tagService.createTag(tagname) : CompletableFuture.completedFuture(tag));
        CompletableFuture<Gamelist> gamelistCompletableFuture = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        CompletableFuture[] completableFutures = {tagCompletableFuture, gamelistCompletableFuture};
        return CompletableFuture.allOf(completableFutures).thenCompose(x -> {
            Gamelist gamelist = gamelistCompletableFuture.join();
            gamelist.addTag(tagCompletableFuture.join());
            return gamelistRepository.save(gamelist);
        });
    }

    /**
     *
     * @param player
     * @param gamelist
     * @param tag
     * @return
     */
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
        CompletableFuture<Tag> tagCompletableFuture = tagService.findTag(tagname);
        CompletableFuture<Gamelist> gamelistCompletableFuture = gamelistRepository.findOneByIdPlayerAndIdName(player, listname);
        CompletableFuture[] completableFutures = {tagCompletableFuture, gamelistCompletableFuture};
        return CompletableFuture.allOf(completableFutures).thenCompose(x -> {
           Gamelist gamelist = gamelistCompletableFuture.join();
           gamelist.removeTag(tagCompletableFuture.join());
           return gamelistRepository.save(gamelist);
        });
    }

    public CompletableFuture<Gamelist> findOne(Gamelist gamelist) {
        return gamelistRepository.findOne(gamelist.getId());
    }
}
