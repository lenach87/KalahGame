package utils;

import mykalah.data.Game;
import mykalah.data.Kalah;
import mykalah.data.Pit;
import mykalah.data.Player;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.*;
import java.util.List;

public class PlayerBuilder {

    private Player model;

    public PlayerBuilder() {
        model = new Player();
    }



    public PlayerBuilder id (long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public PlayerBuilder name(String name) {
        ReflectionTestUtils.setField(model, "name", name);
        return this;
    }

    public PlayerBuilder pitsForPlayer(List<Pit> pitsForPlayer) {
        ReflectionTestUtils.setField(model, "pitsForPlayer", pitsForPlayer);
        return this;
    }

    public PlayerBuilder kalahForPlayer(Kalah kalahForPlayer) {
        ReflectionTestUtils.setField(model, "kalahForPlayer", kalahForPlayer);
        return this;
    }

    public PlayerBuilder inTurn(boolean inTurn) {
        ReflectionTestUtils.setField(model, "inTurn", inTurn);
        return this;
    }

    public Player build() {
        return model;
    }
}
