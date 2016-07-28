package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Player implements Serializable {
    static final long serialVersionUID = 42L;

    public Player() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "PLAYER_ID")
    private long id;

    @Column (unique = true)
    private String name;

    private int[] pitsForPlayer;

    private int kalahForPlayer;

    @Column
    private boolean inTurn;

    public boolean isInTurn() {
        return inTurn;
    }

    public void setInTurn(boolean inTurn) {
        this.inTurn = inTurn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int [] getPitsForPlayer() {
        return pitsForPlayer;
    }

    public void setPitsForPlayer(int [] pitsForPlayer) {
        this.pitsForPlayer = pitsForPlayer;
    }

    public int getKalahForPlayer() {
        return kalahForPlayer;
    }

    public void setKalahForPlayer(int kalahForPlayer) {
        this.kalahForPlayer = kalahForPlayer;
    }
}