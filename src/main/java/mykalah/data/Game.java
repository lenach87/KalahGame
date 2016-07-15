package mykalah.data;

import javax.persistence.*;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private long id;

    @Column
    private Player[] playersOfGame;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player[] getPlayersOfGame() {
        return playersOfGame;
    }

    public void setPlayersOfGame(Player[] playersOfGame) {
        this.playersOfGame = playersOfGame;
    }

    public Game() {
    }
}
