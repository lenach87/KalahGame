package mykalah.data;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table(name = "Players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "USER_ID")
    private long id;

    @Column (name = "USER_Name", unique = true)
    private String name;

    @Column
    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Pit[] pitsForPlayer;

    @Column
    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public Pit[] getPitsForPlayer() {
        return pitsForPlayer;
    }

    public void setPitsForPlayer(Pit[] pitsForPlayer) {
        this.pitsForPlayer = pitsForPlayer;
    }

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