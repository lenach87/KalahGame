package mykalah.service;

import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class PitService {
    @Autowired
    PitRepository pitRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    public List <Pit> save (List <Pit> pits) {
        for (Pit i:pits) {
            pitRepository.save(i);
        }
        return pits;
    }

    public boolean makeMove (long gameId, int number) {
        if (number == 0||number>6) {
            return false;
        }
        String[] players = gameService.findOne(gameId).getPlayersOfGame();
        final Player first = playerService.findPlayerByName(players[0]);
        final Player second = playerService.findPlayerByName(players[1]);
        Player acting;
        Player opposite;
        if (first.isInTurn()) {
            acting = first;
            opposite = second;
        } else if (second.isInTurn()) {
            acting = second;
            opposite = first;
        }
        else {
            first.setInTurn(true);
            second.setInTurn(false);
            acting = first;
            opposite = second;
        }
        List <Pit> pitsForActing = acting.getPitsForPlayer();

        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn = selected.getStonesInPit();

        /**
         * Check if selected pit is empty
         */
        if (amountOfStonesForTurn == 0) {
            return false;
        }
        /**
         * Check if will finish in one's pit before Kalah in first round
         */
        if (amountOfStonesForTurn <= (6 - number)) {
            return makeMoveEndActivePit(number, acting, opposite, 0);
        }
        /**
         * Check if will finish in one's Kalah
         */
        if (amountOfStonesForTurn == (6 - number + 1)) {
            return makeMoveEndActiveKalah(number, acting, opposite, 0);
        }
        /**
         * Check if will finish in opposite pit in first round
         */
        if (amountOfStonesForTurn <= (6 - number + 1 + 6)) {
            return makeMoveEndOppositePit(number, acting, opposite, 0);
        }
        /**
         * Check if will make less than one full round
         */
        if (amountOfStonesForTurn <= 12) {
            return makeMoveEndActingPitNotFullRound (number, acting, opposite, 0);
        }
        /**
         * If will make full round several times
         */
        else {
            int left = amountOfStonesForTurn%13;
            int times = amountOfStonesForTurn/13;
            if (left==0) {
                makeFullMove(number, acting, opposite, times);
                if (checkIfEndGame(acting, opposite)) {
                    return true;
                }
                else {
                    acting.setInTurn(false);
                    opposite.setInTurn(true);
                    return false;
                }
            }
            if (left<=(6 - number)) {
                makeFullMove(number, acting, opposite, times);
                return makeMoveEndActivePit(left, acting, opposite, times);
            }
            if (left == (6 - number + 1)) {
                makeFullMove(number, acting, opposite, times);
                return makeMoveEndActiveKalah(left, acting, opposite, times);
            }
            if (left <= (6 - number + 1 + 6)) {
                makeFullMove(number, acting, opposite, times);
                return makeMoveEndOppositePit(number, acting, opposite, times);
            }
            else {
                makeFullMove(number, acting, opposite, times);
                return makeMoveEndActingPitNotFullRound (number, acting, opposite, times);
            }
        }
    }

    /**
     * Check if end of the game
     */

    public boolean checkIfEndGame (Player actingPlayer, Player oppositePlayer) {

        if (actingPlayer.getKalahForPlayer().getStonesInKalah()>36) {
            return true;
        }
        if (getStonesCount(oppositePlayer)==0) {
            actingPlayer.getKalahForPlayer().setStonesInKalah(actingPlayer.getKalahForPlayer().getStonesInKalah()+getStonesCount(actingPlayer));
            return true;
        }
        else {
            return false;
        }
    }

   /**
     * Count stones in player's pits
     */

    public int getStonesCount (Player player) {
        int i = 0;
        for (Pit pit:player.getPitsForPlayer()) {
            i+=pit.getStonesInPit();
        }
        return i;
    }

    public boolean makeMoveEndActivePit (int number, Player acting, Player opposite, int times) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();

        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn = selected.getStonesInPit();
        if (amountOfStonesForTurn >= (6 - number)) {
            amountOfStonesForTurn = amountOfStonesForTurn%13;
        }
        if (amountOfStonesForTurn < (6 - number)) {
            selected.setStonesInPit(0);
        }
        for (int i = amountOfStonesForTurn, j = 0; i > 0; i--, j++) {
            pitsForActing.get(number + j).setStonesInPit((pitsForActing.get(number + j).getStonesInPit()) + 1);

        }
        if ((pitsForActing.get(number + amountOfStonesForTurn - 1).getStonesInPit() == times) &&
                (pitsForOpposite.get(5 - (number + amountOfStonesForTurn - 1)).getStonesInPit() != 0)) {
                kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah() + 1 + pitsForOpposite.get(5 - (number + amountOfStonesForTurn - 1)).getStonesInPit());
                pitsForOpposite.get(5 - (number + amountOfStonesForTurn - 1)).setStonesInPit(0);
                pitsForActing.get(number + amountOfStonesForTurn - 1).setStonesInPit(0);
        }

        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }

    public boolean makeMoveEndActiveKalah (int number, Player acting, Player opposite, int times) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();
        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn = selected.getStonesInPit();
        if (amountOfStonesForTurn>(6 - number + 1 + 6)) {
            amountOfStonesForTurn = amountOfStonesForTurn%13;
        }
        selected.setStonesInPit(0+times);
        for (int i = amountOfStonesForTurn, j = 0; i > 1; i--, j++) {
            pitsForActing.get(number + j).setStonesInPit((pitsForActing.get(number + j).getStonesInPit()) + 1);
        }
        kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah() + 1);
        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean makeMoveEndOppositePit (int number, Player acting, Player opposite, int times) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();

        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn = selected.getStonesInPit();
        if (amountOfStonesForTurn>(6 - number + 1)) {
            amountOfStonesForTurn = amountOfStonesForTurn%13;
        }
        selected.setStonesInPit(0+times);
        int usedForActing = 0;
        for (int i = number, j = 0; i < 6; i++, j++) {
            pitsForActing.get(i).setStonesInPit((pitsForActing.get(i).getStonesInPit()) + 1);
            usedForActing = j;
        }
        kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah() + 1);
        usedForActing += 1;
        for (int i = (amountOfStonesForTurn - usedForActing - 1), j = 0; i > 0; i--, j++) {
            pitsForOpposite.get(j).setStonesInPit((pitsForOpposite.get(j).getStonesInPit()) + 1);
        }
        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }
    public boolean makeMoveEndActingPitNotFullRound (int number, Player acting, Player opposite, int times) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();
        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn = selected.getStonesInPit();
        selected.setStonesInPit(0+times);
        kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah() + 1);
        for (int i = 6, j = 0; i > 0; i--, j++) {
            pitsForOpposite.get(j).setStonesInPit((pitsForOpposite.get(j).getStonesInPit()) + 1);
        }
        int leftForActing = amountOfStonesForTurn - 6 + 1;
        int forFirstIteration = 6 - number;
        int forSecondIteration = leftForActing - forFirstIteration;
        for (int i = forFirstIteration, j = 0; i > 0; i--, j++) {
            pitsForActing.get(number + j).setStonesInPit((pitsForActing.get(number + j).getStonesInPit()) + 1);
        }
        for (int i = forSecondIteration, j = 0; i > 0; i--, j++) {
            pitsForActing.get(j).setStonesInPit((pitsForActing.get(j).getStonesInPit()) + 1);
        }

        if ((pitsForActing.get(forSecondIteration - 1).getStonesInPit() == times) &&
                    (pitsForOpposite.get(5 - (forSecondIteration - 1)).getStonesInPit() != 0)) {
                kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah() + 1 + pitsForOpposite.get(5 - (forSecondIteration - 1)).getStonesInPit());
                pitsForOpposite.get(5 - (forSecondIteration - 1)).setStonesInPit(0);
                pitsForActing.get(forSecondIteration - 1).setStonesInPit(0);
        }

        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }
        public void makeFullMove (int number, Player acting, Player opposite, int times) {

            List <Pit> pitsForActing = acting.getPitsForPlayer();
            List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
            Kalah kalahForActing = acting.getKalahForPlayer();
            Pit selected = pitsForActing.get(number - 1);
            selected.setStonesInPit(0);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForActing.get(j).setStonesInPit(pitsForActing.get(j).getStonesInPit()+times);
            }
            kalahForActing.setStonesInKalah(kalahForActing.getStonesInKalah()+times);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForOpposite.get(j).setStonesInPit(pitsForOpposite.get(j).getStonesInPit()+times);
            }
        }
    }
