package mykalah.service;

import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class PlayerService {

    public PlayerService() {
    }

    @Autowired
    private PlayerRepository playerRepository;

    public Player findPlayerByName(String username) {
        return playerRepository.findPlayerByName(username);
    }

    public Player save (Player player) {return playerRepository.save(player);}

}
