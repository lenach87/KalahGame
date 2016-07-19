package mykalah.service;

import java.util.List;
import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private KalahService kalahService;

    @Autowired
    private PitService pitService;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findPlayerByName(String username) {
        return playerRepository.findPlayerByName(username);
    }

    public Player findOne (long id) {return playerRepository.findOne(id);}

    public Player save (Player player) {return playerRepository.save(player);}

}
