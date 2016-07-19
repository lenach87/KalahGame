package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Move implements Serializable {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MOVE_ID")
    private long id;

    @Column
    private long gameId;
    @Column
    private String actingPlayer;
    @Column
    private String oppositePlayer;
    @Column
    private int numberOfPit;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "MovesAsActing")
    private Player actingPlayer1;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "MovesAsOpposite")
    private Player oppositePlayer1;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "GameForMoves")
    private Game gameForMoves;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Game getGameForMoves() {
        return gameForMoves;
    }

    public void setGameForMoves(Game gameForMoves) {
        this.gameForMoves = gameForMoves;
    }
    public Player getActingPlayer1() {
        return actingPlayer1;
    }
    public void setActingPlayer1(Player actingPlayer1) {
        this.actingPlayer1 = actingPlayer1;
    }

    public Player getOppositePlayer1() {
        return oppositePlayer1;
    }

    public void setOppositePlayer1(Player oppositePlayer1) {
        this.oppositePlayer1 = oppositePlayer1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getActingPlayer() {
        return actingPlayer;
    }

    public void setActingPlayer(String actingPlayer) {
        this.actingPlayer = actingPlayer;
    }

    public String getOppositePlayer() {
        return oppositePlayer;
    }

    public void setOppositePlayer(String oppositePlayer) {
        this.oppositePlayer = oppositePlayer;
    }

    public int getNumberOfPit() {
        return numberOfPit;
    }

    public void setNumberOfPit(int numberOfPit) {
        this.numberOfPit = numberOfPit;
    }

    public Move() {
    }
}
