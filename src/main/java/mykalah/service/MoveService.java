package mykalah.service;


import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;

@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class MoveService {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private MoveRepository moveRepository;
    public Move saveMove (long gameId, String actingPlayer, String oppositePlayer, int numberOfPit) {
        Move move = new Move();
        move.setGameId(gameId);
        move.setActingPlayer(actingPlayer);
        move.setOppositePlayer(oppositePlayer);
        move.setNumberOfPit(numberOfPit);
        move.setActingPlayer1(playerService.findPlayerByName(actingPlayer));
        move.setOppositePlayer1(playerService.findPlayerByName(oppositePlayer));
        return moveRepository.save(move);
    }
}

