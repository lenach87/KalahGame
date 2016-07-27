package mykalah.service;

import mykalah.data.*;
import java.util.List;


public interface GameService {

    Game findOne (long id);
    Game makeGame (String nameActing, String nameOpposite);
    Game updateGame (long id, int i);
    Player createNewActingPlayer (String name);
    Player createNewOppositePlayer (String name);
    Kalah createNewKalah ();
    List <Pit> createNewPits ();
}
