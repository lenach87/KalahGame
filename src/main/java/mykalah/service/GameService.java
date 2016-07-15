package mykalah.service;

import mykalah.data.Game;
import mykalah.data.GameRepository;
import mykalah.data.PitRepository;
import mykalah.data.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    public Game findOne (long id) {
        return gameRepository.findOne(id);
    }

    public Game makeGame (String nameActing, String nameOpposite) {
        Game game = new Game();
        Player playerActing = playerService.findPlayerByName(nameActing);
        Player playerOpposite = playerService.findPlayerByName(nameOpposite);
        game.setPlayersOfGame(new Player[]{playerActing, playerOpposite});
        playerActing.setInTurn(true);
        return game;
    }
}
