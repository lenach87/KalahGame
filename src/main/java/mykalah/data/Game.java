package mykalah.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Entity
public class Game implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private long id;

    @Column
    private String[] playersOfGame;

    @Column
    private String player1;

    @Column
    private String player2;


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
