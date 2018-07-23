package org.gamelog.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.gamelog.config.GameDeserializer;
import org.gamelog.model.Game;
import org.gamelog.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.get;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    private final String API_KEY = "71aeb38d5afbf91d50825fc9c24e86ff";
    private AsyncHttpClient asyncHttpClient = asyncHttpClient();

    private Gson gson = new GsonBuilder().registerTypeAdapter(Game.class, new GameDeserializer()).create();

    /**
     * Obtains game info from:
     *  - The external API, IGDB, if there's no info of the game in the database. The info is then stored in the database for future access.
     *  - The database, if this information was stored in it in a previous IGDB request.
     * @param id The numeric identifier of the game.
     * @return A completable future containing a game object, which has information about the game.
     */
    public CompletableFuture<Game> findGameInfoById(Long id){
        return gameRepository.exists(id).thenCompose(bool -> {
            if (bool) {
                return gameRepository.findOne(id);
            } else {
                String uri = "https://api-endpoint.igdb.com/games/" + id + "?fields=name,summary,cover";
                Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

                return asyncHttpClient
                        .executeRequest(request)
                        .toCompletableFuture()
                        .thenCompose(response -> {
                            Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                            // API always returns a collection, even if there's only one element.
                            List<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                            Game game = games.get(0);
                            return gameRepository.save(game);
                        });
            }
        });
    }

    public CompletableFuture<Iterable<Game>> search(String query, int page){
        String uri = "https://api-endpoint.igdb.com/games/?search=" + query + "&fields=name,cover&offset=" + page*10;
        Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

        return asyncHttpClient
                .executeRequest(request)
                .toCompletableFuture()
                .thenApply(response -> {
                    Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                    ArrayList<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                    return games;
                });
    }

    public CompletableFuture<Game> findGameById(Long gameid) {
        return gameRepository.findOne(gameid);
    }
}
