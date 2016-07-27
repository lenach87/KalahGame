package utils;

import mykalah.data.Kalah;
import mykalah.data.Pit;
import mykalah.data.Player;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.*;

public class KalahBuilder {

    private Kalah model;

    public KalahBuilder() {
        model = new Kalah();
    }

    public KalahBuilder id (long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public KalahBuilder stonesInKalah(int number) {
        ReflectionTestUtils.setField(model, "stonesInKalah", number);
        return this;
    }

//    public KalahBuilder playerOfKalah(Player player) {
//        ReflectionTestUtils.setField(model, "playerOfKalah", player);
//        return this;
//    }

    public Kalah build() {
        return model;
    }

}
