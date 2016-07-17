package mykalah.service;



import java.util.List;


import mykalah.data.Kalah;
import mykalah.data.Pit;
import mykalah.data.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mykalah.data.PlayerRepository;


@Service
public class PlayerService
{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private KalahService kalahService;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findPlayerByName(String username) {
        return playerRepository.findPlayerByName(username);
    }

    public Player findOne (long id) {return playerRepository.findOne(id);}

 //   public void delete (long id) {
 //       playerRepository.delete(id);
 //   }

    public Player createNewPlayer (String name) {
        if (playerRepository.findPlayerByName(name)!=null) {
            return playerRepository.findPlayerByName(name);
        }
        else {
            Player newPlayer = new Player();
            newPlayer.setName(name);
            newPlayer.setPitsForPlayer(new Pit[6]);
            newPlayer.setKalahForPlayer(kalahService.createNewKalah(0));
            //  playerRepository.saveAndFlush(newPlayer);
            for (Pit pit : newPlayer.getPitsForPlayer()) {
                pit.setStonesInPit(6);
            }
            return newPlayer;
        }
    }
}
