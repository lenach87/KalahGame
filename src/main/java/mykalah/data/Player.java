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

    private int[] pitsForPlayer;

    private int kalahForPlayer;

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

    public List<Game> getGamesAsInitialPlayer1() {
        return gamesAsInitialPlayer1;
    }

    public void setGamesAsInitialPlayer1(List<Game> gamesAsInitialPlayer1) {
        this.gamesAsInitialPlayer1 = gamesAsInitialPlayer1;
    }

    public List<Game> getGamesAsInitialPlayer2() {
        return gamesAsInitialPlayer2;
    }

    public void setGamesAsInitialPlayer2(List<Game> gamesAsInitialPlayer2) {
        this.gamesAsInitialPlayer2 = gamesAsInitialPlayer2;
    }
}