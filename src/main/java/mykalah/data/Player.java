package mykalah.data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "Players")
public class Player implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "PLAYER_ID")
    private long id;

    @Column (unique = true)
    private String name;

   // @Column(name = "pits_player")
    @OneToMany (mappedBy = "playerOfPits", cascade = CascadeType.ALL)
    private List<Pit> pitsForPlayer;


    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "Kalah")
    private Kalah kalahForPlayer;

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

    public List<Pit> getPitsForPlayer() {
        return pitsForPlayer;
    }

    public void setPitsForPlayer(List<Pit> pitsForPlayer) {this.pitsForPlayer = pitsForPlayer;}

    public Kalah getKalahForPlayer() {
        return kalahForPlayer;
    }

    public void setKalahForPlayer(Kalah kalahForPlayer) {
        this.kalahForPlayer = kalahForPlayer;
    }

    public Player() {}

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}