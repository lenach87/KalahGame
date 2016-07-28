package mykalah.service;

import mykalah.data.Player;
import mykalah.data.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class PlayerServiceImpl implements PlayerService {

    public PlayerServiceImpl() {
    }

    @Autowired
    private PlayerRepository playerRepository;

    public Player findPlayerByName(String username) {
        return playerRepository.findPlayerByName(username);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

}
