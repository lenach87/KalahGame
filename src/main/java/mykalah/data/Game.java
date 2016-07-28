package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Game implements Serializable {
    static final long serialVersionUID = 42L;

    public Game() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Player initialFirstPlayer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Player initialSecondPlayer;

    @Column
    private boolean asFirst;

    @Column
    private String winner;

    @Column
    private int numberOfPitForLastMove;

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getNumberOfPitForLastMove() {
        return numberOfPitForLastMove;
    }

    public void setNumberOfPitForLastMove(int numberOfPitForLastMove) {
        this.numberOfPitForLastMove = numberOfPitForLastMove;
    }

    public boolean isAsFirst() {
        return asFirst;
    }

    public void setAsFirst(boolean asFirst) {
        this.asFirst = asFirst;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getInitialFirstPlayer() {
        return initialFirstPlayer;
    }

    public void setInitialFirstPlayer(Player initialFirstPlayer) {
        this.initialFirstPlayer = initialFirstPlayer;
    }

    public Player getInitialSecondPlayer() {
        return initialSecondPlayer;
    }

    public void setInitialSecondPlayer(Player initialSecondPlayer) {
        this.initialSecondPlayer = initialSecondPlayer;
    }

    public String getWinner() {
        return winner;
    }

}
