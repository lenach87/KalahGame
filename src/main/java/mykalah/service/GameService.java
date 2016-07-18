package mykalah.service;

import mykalah.data.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by o.chubukina on 14/07/2016.
 */
@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class GameService {

    public GameService () {}

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PitService pitService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private KalahService kalahService;


    public Game findOne (Long id) {
        return gameRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Game makeGame (String nameActing, String nameOpposite) {

        //  TransactionTemplate template = new TransactionTemplate(this.txManager);
        //  em.getTransaction().begin();
        //   Game execute = template.execute(new TransactionCallback<Game>() {
        //   public Game doInTransaction(TransactionStatus status) {

        Game game = new Game();
        createNewPlayer(nameActing);
        createNewPlayer(nameOpposite);
        game.setPlayer1(nameActing);
        game.setPlayer2(nameOpposite);
        game.setPlayersOfGame(new String[]{nameActing, nameOpposite});
        gameRepository.saveAndFlush(game);
        //   playerService.findPlayerByName(nameActing).setInTurn(true);
        return game;
    }
    @Transactional
    public Player createNewPlayer (String name) {
        Player newPlayer = new Player();
        newPlayer.setName(name);
        Kalah kalah = createNewKalah();
        newPlayer.setKalahForPlayer(kalah);
        kalah.setPlayerOfKalah(newPlayer);
        List <Pit> pits = createNewPits();
        newPlayer.setPitsForPlayer(pits);
        for (Pit pit:pits) {
            pit.setPlayerOfPits(newPlayer);
        }
        return playerService.saveAndFlush(newPlayer);
    }
    @Transactional
    public Kalah createNewKalah () {
        Kalah kalah = new Kalah();
        kalah.setStonesInKalah(0);
    //    kalah.setPlayerOfKalah(player);
        return  kalahService.saveAndFlush(kalah);
    }
    @Transactional
    public List <Pit> createNewPits () {

        List <Pit> pits = new ArrayList<>();
        for (int i=0; i<6; i++) {
            Pit p = new Pit();
            pits.add(p);
            p.setStonesInPit(6);
        }
        return pitService.save(pits);
    }
}
