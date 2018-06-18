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

    public CompletableFuture<Game> findGameInfoById(Long id){
        CompletableFuture<Game> gameCompletableFuture = new CompletableFuture<>();

        if(gameRepository.exists(id)) {
            Game game = gameRepository.findOne(id);
            gameCompletableFuture.complete(game);
        } else {
            String uri = "https://api-endpoint.igdb.com/games/" + id + "?fields=name,summary,cover";
            Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

            // Fetch data from API using AsyncHttpClient and Gson
            gameCompletableFuture = asyncHttpClient
                    .executeRequest(request)
                    .toCompletableFuture()
                    .thenApply(response -> {
                        Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                        List<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                        Game game = games.get(0);
                        gameRepository.save(games.get(0));
                        return games.get(0);
                    });
        }
        return gameCompletableFuture;
    }

    public CompletableFuture<ArrayList<Game>> search(String query, int page){
        String uri = "https://api-endpoint.igdb.com/games/?search=" + query + "&fields=name,cover&offset=" + page*10;
        Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

        CompletableFuture<ArrayList<Game>> completableFuture = asyncHttpClient
                .executeRequest(request)
                .toCompletableFuture()
                .thenApply(response -> {
                    Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                    ArrayList<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                    return games;
                });
        return completableFuture;
    }

    public CompletableFuture<ArrayList<Game>> findRecentGames() {
        String uri = "https://api-endpoint.igdb.com/games/?fields=name,cover&order=release_dates.date:desc&limit=5";
        Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

        CompletableFuture<ArrayList<Game>> completableFuture = asyncHttpClient
                .executeRequest(request)
                .toCompletableFuture()
                .thenApply(response -> {
                    Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                    ArrayList<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                    return games;
                });
        return completableFuture;
    }

    public Game findGameById(Long gameid) {
        return gameRepository.findOne(gameid);
    }
}
