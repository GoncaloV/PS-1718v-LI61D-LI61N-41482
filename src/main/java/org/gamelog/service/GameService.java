package org.gamelog.service;

import callback.OnSuccessCallback;
import groovy.transform.ThreadInterrupt;
import org.gamelog.model.Game;
import org.gamelog.repos.GameRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrapper.IGDBWrapper;
import wrapper.Parameters;
import wrapper.Version;

import java.util.concurrent.CountDownLatch;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    private IGDBWrapper wrapper = new IGDBWrapper("71aeb38d5afbf91d50825fc9c24e86ff", Version.STANDARD, false);

    // To wait on callback
    private boolean resultReady = false;

    // Fazer sempre pedidos à API ou guardar dados na base de dados?
    public Game getGameInfoById(Long id){
        Parameters params = new Parameters().addIds(id.toString()).addFields("name, summary, cover");
        Game game;
        if(gameRepository.exists(id)) {
            game = gameRepository.findOne(id);
        } else {
            game = new Game(id);
            gameRepository.save(game);
        }

        // Fetch data from API
        // Is this how callbacks work here?
        resultReady = false;
        wrapper.games(params, new OnSuccessCallback(){
            @Override
            public void onSuccess(JSONArray result) {
                JSONObject jsonGame = result.getJSONObject(0);
                String name = jsonGame.getString("name");
                String summary = jsonGame.getString("summary");
                JSONObject cover = jsonGame.getJSONObject("cover");
                String imageUrl = cover.getString("url");
                game.setName(name);
                game.setSummary(summary);
                game.setCoverUrl(imageUrl);
                resultReady = true;
            }

            @Override
            public void onError(Exception error) {
                // What TODO?
            }
        });

        while(!resultReady){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return game;
    }
}
