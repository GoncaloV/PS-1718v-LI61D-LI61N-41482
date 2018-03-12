package org.gamelog.model;

import java.io.Serializable;
import java.util.Objects;

public class EntryId implements Serializable {
    private Player playerId;
    private Game gameId;

    public EntryId() {}

    public EntryId(Player playerId, Game gameId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        Entry assignedRole = (Entry) o;
        return Objects.equals(playerId, assignedRole.getPlayerId()) &&
                Objects.equals(gameId, assignedRole.getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
