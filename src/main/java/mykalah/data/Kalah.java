package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Kalah implements Serializable {
    static final long serialVersionUID = 42L;

    public Kalah() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KALAH_ID")
    private long id;

    @OneToOne (mappedBy = "kalahForPlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Player playerOfKalah;

    @Column
    private int stonesInKalah;

    public int getStonesInKalah() {
        return stonesInKalah;
    }

    public void setStonesInKalah(int stonesInKalah) {
        this.stonesInKalah = stonesInKalah;
    }

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

}
