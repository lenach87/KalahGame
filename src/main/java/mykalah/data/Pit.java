package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Entity
public class Pit implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PIT_ID")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "PitsOfPlayer")
    private Player playerOfPits;

    @Column
    private int stonesInPit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayerOfPits() {
        return playerOfPits;
    }

    public void setPlayerOfPits(Player playerOfPits) {
        this.playerOfPits = playerOfPits;
    }

    public int getStonesInPit() {
        return stonesInPit;
    }

    public void setStonesInPit(int stonesInPit) {
        this.stonesInPit = stonesInPit;
    }

    public Pit() {
    }
}
