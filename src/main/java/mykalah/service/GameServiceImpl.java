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
public class GameServiceImpl implements GameService {

    public GameServiceImpl() {}

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerServiceImpl playerService;

    public Game findOne (long id) {
        return gameRepository.findOne(id);
    }

    @Transactional
    public Game makeGame (String nameActing, String nameOpposite) {

        Game game = new Game();
        playerService.save(createNewActingPlayer(nameActing));
        playerService.save(createNewOppositePlayer(nameOpposite));
        game.setInitialFirstPlayer(playerService.findPlayerByName(nameActing));
        game.setInitialSecondPlayer(playerService.findPlayerByName(nameOpposite));
        game.setAsFirst(true);
        gameRepository.saveAndFlush(game);
        return game;
    }

    @Transactional
    public Game updateGame (long id, int i) {

        Game thisGame = gameRepository.findOne(id);
        thisGame.setNumberOfPitForLastMove(i);
        return thisGame;
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
