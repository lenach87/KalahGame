package utils;

import mykalah.data.Pit;
import mykalah.data.Player;
import org.springframework.test.util.ReflectionTestUtils;

public class PitBuilder {

    private Pit model;

    public PitBuilder() {
        model = new Pit();
    }

    public PitBuilder id (long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public PitBuilder stonesInPit(int number) {
        ReflectionTestUtils.setField(model, "stonesInPit", number);
        return this;
    }

//    public PitBuilder playerOfPit(Player player) {
//        ReflectionTestUtils.setField(model, "playerOfPits", player);
//        return this;
//    }

    public Pit build() {
        return model;
    }
}
