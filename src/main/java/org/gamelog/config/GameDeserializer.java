package org.gamelog.config;

import com.google.gson.*;
import org.gamelog.model.Game;

import java.lang.reflect.Type;

public class GameDeserializer implements JsonDeserializer<Game> {
    @Override
    public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();

        JsonElement aux = jsonObject.get("summary");
        String summary = aux != null ? aux.getAsString() : null;

        aux = jsonObject.get("cover");
        String coverUrl = aux == null ? "/nocover.png" : aux.getAsJsonObject().get("url").getAsString();

        Game game = new Game(id);
        game.setName(name);
        game.setSummary(summary);
        game.setCoverUrl(coverUrl);
        return game;
    }
}
