package mykalah.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Entity
public class Kalah {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long id;

    @OneToOne
    private Player playerOfKalah;

    public int getStonesInKalah() {
        return stonesInKalah;
    }

    public void setStonesInKalah(int stonesInKalah) {
        this.stonesInKalah = stonesInKalah;
    }

    private int stonesInKalah;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayerOfKalah() {
        return playerOfKalah;
    }

    public void setPlayerOfKalah(Player playerOfKalah) {
        this.playerOfKalah = playerOfKalah;
    }

    public Kalah() {
    }

}
