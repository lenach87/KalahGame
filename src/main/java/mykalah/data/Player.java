package mykalah.data;

import javax.persistence.*;

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

    public List<Move> getMovesAsActingPlayer() {
        return movesAsActingPlayer;
    }

    public void setMovesAsActingPlayer(List<Move> movesAsActingPlayer) {
        this.movesAsActingPlayer = movesAsActingPlayer;
    }

    public List<Move> getMovesAsOppositePlayer() {
        return movesAsOppositePlayer;
    }

    public void setMovesAsOppositePlayer(List<Move> movesAsOppositePlayer) {
        this.movesAsOppositePlayer = movesAsOppositePlayer;
    }

    public List<Game> getGameAsPlayer1() {
        return gameAsPlayer1;
    }

    public void setGameAsPlayer1(List<Game> gameAsPlayer1) {
        this.gameAsPlayer1 = gameAsPlayer1;
    }

    public List<Game> getGameAsPlayer2() {
        return gameAsPlayer2;
    }

    public void setGameAsPlayer2(List<Game> gameAsPlayer2) {
        this.gameAsPlayer2 = gameAsPlayer2;
    }

    @Column (unique = true)

    private String name;

    @OneToMany (mappedBy = "playerOfPits", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Pit> pitsForPlayer;


    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "Kalah")
    private Kalah kalahForPlayer;

    @OneToMany (mappedBy = "actingPlayer1", cascade = CascadeType.ALL)
    private List<Move> movesAsActingPlayer;

    @OneToMany (mappedBy = "oppositePlayer1", cascade = CascadeType.ALL)
    private List<Move> movesAsOppositePlayer;

    @OneToMany (mappedBy = "initialPlayer1", cascade = CascadeType.ALL)
    private List <Game> gameAsPlayer1;

    @OneToMany (mappedBy = "initialPlayer2", cascade = CascadeType.ALL)
    private List <Game> gameAsPlayer2;

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