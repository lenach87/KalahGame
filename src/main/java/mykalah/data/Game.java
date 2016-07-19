package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Game implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private long id;

    public Player getInitialPlayer1() {
        return initialPlayer1;
    }

    public void setInitialPlayer1(Player initialPlayer1) {
        this.initialPlayer1 = initialPlayer1;
    }

    public Player getInitialPlayer2() {
        return initialPlayer2;
    }

    public void setInitialPlayer2(Player initialPlayer2) {
        this.initialPlayer2 = initialPlayer2;
    }

    @Column
    private String[] playersOfGame;

    @Column
    private String player1;

    @Column
    private String player2;

    @OneToMany(mappedBy = "gameForMoves", cascade = CascadeType.ALL)
    private List<Move> moves;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "InitialPlayer1")
    private Player initialPlayer1;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "InitialPlayer2")
    private Player initialPlayer2;

    @Column
    private boolean asFirst;
    @Column
    private String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getTempNumber() {
        return tempNumber;
    }

    public void setTempNumber(int tempNumber) {
        this.tempNumber = tempNumber;
    }

    @Column
    int tempNumber;

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
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

    public String[] getPlayersOfGame() {
        return playersOfGame;
    }

    public void setPlayersOfGame(String[] playersOfGame) {
        this.playersOfGame = playersOfGame;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return  player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Game() {
    }
}
