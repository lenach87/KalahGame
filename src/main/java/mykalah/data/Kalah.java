package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Entity
public class Kalah implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KALAH_ID")
    private long id;

    @OneToOne (mappedBy = "kalahForPlayer", cascade = CascadeType.ALL)
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
