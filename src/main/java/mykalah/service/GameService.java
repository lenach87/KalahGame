package mykalah.service;

import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


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

    @Transactional
    public Game makeGame (String nameActing, String nameOpposite) {

        Game game = new Game();
        playerService.save(createNewActingPlayer(nameActing));
        playerService.save(createNewOppositePlayer(nameOpposite));
        game.setPlayer1(nameActing);
        game.setPlayer2(nameOpposite);
        game.setPlayersOfGame(new String[]{nameActing, nameOpposite});
        game.setInitialPlayer1(playerService.findPlayerByName(nameActing));
        game.setInitialPlayer2(playerService.findPlayerByName(nameOpposite));
        game.setAsFirst(true);
        gameRepository.saveAndFlush(game);
        return game;
    }

    @Transactional
    public Game updateGame (Game game, int i) {

        Game thisgame = gameRepository.findOne(game.getId());
        thisgame.setTempNumber(i);
        return thisgame;
    }

    @Transactional
    public Player createNewActingPlayer (String name) {
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
        newPlayer.setInTurn(true);
        return newPlayer;
    }
    @Transactional
    public Player createNewOppositePlayer (String name) {
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
        newPlayer.setInTurn(false);
        return newPlayer;
    }

    @Transactional
    public Kalah createNewKalah () {
        Kalah kalah = new Kalah();
        kalah.setStonesInKalah(0);
        return  kalah;
    }
    @Transactional
    public List <Pit> createNewPits () {

        List <Pit> pits = new ArrayList<>();
        for (int i=0; i<6; i++) {
            Pit p = new Pit();
            pits.add(p);
            p.setStonesInPit(6);
        }
        return pits;
    }
}
