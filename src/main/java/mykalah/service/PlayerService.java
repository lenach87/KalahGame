package mykalah.service;

import mykalah.data.Player;

public interface PlayerService {

    Player findPlayerByName(String username);
    Player save (Player player);

}
