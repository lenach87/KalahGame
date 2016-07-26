package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Players")
public class Player implements Serializable {
    static final long serialVersionUID = 42L;

    public Player() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "PLAYER_ID")
    private long id;

    @Column (unique = true)
    private String name;

    @OneToMany (mappedBy = "playerOfPits", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Pit> pitsForPlayer;

    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "Kalah")
    private Kalah kalahForPlayer;

    @OneToMany (mappedBy = "initialFirstPlayer", cascade = CascadeType.ALL)
    private List <Game> gamesAsInitialPlayer1;

    @OneToMany (mappedBy = "initialSecondPlayer", cascade = CascadeType.ALL)
    private List <Game> gamesAsInitialPlayer2;

    @Column
    private boolean inTurn;

    public List<Game> getGameAsPlayer1() {
        return gamesAsInitialPlayer1;
    }

    public void setGameAsPlayer1(List<Game> gameAsPlayer1) {
        this.gamesAsInitialPlayer1 = gameAsPlayer1;
    }

    public List<Game> getGameAsPlayer2() {
        return gamesAsInitialPlayer2;
    }

    public void setGameAsPlayer2(List<Game> gameAsPlayer2) {
        this.gamesAsInitialPlayer2 = gameAsPlayer2;
    }

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

}