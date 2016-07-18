package mykalah.service;



import java.util.List;


import mykalah.data.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


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

    public Player saveAndFlush (Player player) {return playerRepository.saveAndFlush(player);}

    //   public void delete (long id) {
    //       playerRepository.delete(id);
    //   }

}
