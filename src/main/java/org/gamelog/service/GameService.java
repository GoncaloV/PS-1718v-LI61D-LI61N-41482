package org.gamelog.service;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.gamelog.GameDeserializer;
import org.gamelog.model.Game;
import org.gamelog.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrapper.IGDBWrapper;
import wrapper.Version;
import com.google.gson.Gson;
import org.asynchttpclient.*;
import static org.asynchttpclient.Dsl.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    private final String DEFAULT_SIZE = "thumb";
    private final String DESIRED_SIZE = "cover_big";

    private final String API_KEY = "71aeb38d5afbf91d50825fc9c24e86ff";
    private AsyncHttpClient asyncHttpClient = asyncHttpClient();

    private Gson gson = new GsonBuilder().registerTypeAdapter(Game.class, new GameDeserializer()).create();

    public CompletableFuture<Game> getGameInfoById(Long id){
        CompletableFuture<Game> gameCompletableFuture = new CompletableFuture<>();

        if(gameRepository.exists(id)) {
            Game game = gameRepository.findOne(id);
            gameCompletableFuture.complete(game);
        } else {
            String uri = "https://api-endpoint.igdb.com/games/" + id + "?fields=name,summary,cover";
            Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

            // Fetch data from API using AsyncHttpClient and Gson
            asyncHttpClient
                    .executeRequest(request)
                    .toCompletableFuture()
                    .thenAccept(response -> {
                        Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                        List<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                        Game game = games.get(0);
                        game.setCoverUrl(game.getCoverUrl().replace(DEFAULT_SIZE, DESIRED_SIZE));
                        gameRepository.save(games.get(0));
                        gameCompletableFuture.complete(games.get(0));
                    }).join();
        }
        return gameCompletableFuture;
    }

    public CompletableFuture<ArrayList<Game>> search(String query, int page){
        CompletableFuture<ArrayList<Game>> completableFuture = new CompletableFuture<>();
        String uri = "https://api-endpoint.igdb.com/games/?search=" + query + "&fields=name,summary,cover&offset=" + page*10;
        Request request = get(uri).addHeader("user-key", API_KEY).addHeader("Accept", "application/json").build();

        asyncHttpClient
                .executeRequest(request)
                .toCompletableFuture()
                .thenAccept(response -> {
                    Type gameListType = new TypeToken<Collection<Game>>(){}.getType();
                    ArrayList<Game> games = gson.fromJson(response.getResponseBody(), gameListType);
                    completableFuture.complete(games);
                }).join();
        return completableFuture;
    }
}
