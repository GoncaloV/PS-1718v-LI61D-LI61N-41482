package org.gamelog.service;

import callback.OnSuccessCallback;
import org.gamelog.model.Game;
import org.gamelog.repos.GameRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrapper.Endpoints;
import wrapper.IGDBWrapper;
import wrapper.Parameters;
import wrapper.Version;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    // IGDB query
    private IGDBWrapper wrapper = new IGDBWrapper("71aeb38d5afbf91d50825fc9c24e86ff", Version.STANDARD, false);
    private final String DEFAULT_SIZE = "thumb";
    private final String DESIRED_SIZE = "cover_big";
    private final String ORDER = "popularity:desc";

    public CompletableFuture<Game> getGameInfoById(Long id){
        Parameters params = new Parameters().addIds(id.toString()).addFields("name, summary, cover");
        CompletableFuture<Game> gameCompletableFuture = new CompletableFuture<>();

        Game game;
        if(gameRepository.exists(id)) {
            game = gameRepository.findOne(id);
        } else {
            game = new Game(id);
            gameRepository.save(game);
        }

        // Fetch data from API
        wrapper.games(params, new OnSuccessCallback(){
            @Override
            public void onSuccess(JSONArray result) {
                JSONObject jsonGame = result.getJSONObject(0);
                String name = jsonGame.getString("name");
                String summary = jsonGame.getString("summary");
                JSONObject cover = jsonGame.getJSONObject("cover");
                String imageUrl = cover.getString("url").replace(DEFAULT_SIZE, DESIRED_SIZE);
                game.setName(name);
                game.setSummary(summary);
                game.setCoverUrl(imageUrl);
                gameCompletableFuture.complete(game);
            }

            @Override
            public void onError(Exception error) {
                gameCompletableFuture.completeExceptionally(error);
            }
        });
        return gameCompletableFuture;
    }

    public CompletableFuture<ArrayList<Game>> search(String query, int page){
        Parameters params = new Parameters().addSearch(query).addFields("id, name, cover").addOrder(ORDER).addOffset(String.valueOf(page*10));
        CompletableFuture<ArrayList<Game>> completableFuture = new CompletableFuture<>();

        wrapper.search(Endpoints.GAMES, params, new OnSuccessCallback() {
            @Override
            public void onSuccess(@NotNull JSONArray jsonArray) {
                ArrayList<Game> games = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    long id = jsonObject.getLong("id");
                    String title = jsonObject.getString("name");
                    JSONObject cover = jsonObject.optJSONObject("cover");
                    String url = cover != null ? cover.optString("url") : null;
                    games.add(new Game(id, title, url));
                }
                completableFuture.complete(games);
            }

            @Override
            public void onError(@NotNull Exception error) {
                completableFuture.completeExceptionally(error);
            }
        });

        return completableFuture;
    }
}
