package utils;

import mykalah.data.Game;
import mykalah.data.Player;
import org.springframework.test.util.ReflectionTestUtils;

public class GameBuilder {

    private Game model;

    public GameBuilder() {
        model = new Game();
    }

    public GameBuilder id (long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public GameBuilder initialFirstPlayer(Player firstPlayer) {
        ReflectionTestUtils.setField(model, "initialFirstPlayer", firstPlayer);
        return this;
    }

    public GameBuilder initialSecondPlayer(Player secondPlayer) {
        ReflectionTestUtils.setField(model, "initialSecondPlayer", secondPlayer);
        return this;
    }

    public GameBuilder asFirst(boolean asFirst) {
        ReflectionTestUtils.setField(model, "asFirst", asFirst);
        return this;
    }

    public GameBuilder numberOfPitForLastMove(int numberOfPitForLastMove) {
        ReflectionTestUtils.setField(model, "numberOfPitForLastMove", numberOfPitForLastMove);
        return this;
    }

    public GameBuilder winner(String winner) {
        ReflectionTestUtils.setField(model, "winner", winner);
        return this;
    }

    public Game build() {
        return model;
    }
}
