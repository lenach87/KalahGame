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
    public PitService() {
    }

    @Autowired
    PitRepository pitRepository;

    @Autowired
    private GameService gameService;

    public boolean makeMove (long gameId, int number) {
        if (number == 0||number>6) {
            return false;
        }

        final Player first = gameService.findOne(gameId).getInitialFirstPlayer();
        final Player second = gameService.findOne(gameId).getInitialSecondPlayer();
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

        /* Check if selected pit is empty
         */
        if (amountOfStonesForTurn == 0) {
            return false;
        }
        /* Check if will finish in one's pit before Kalah in first round
         */
        if (amountOfStonesForTurn <= (6 - number)) {
            return makeMoveEndActivePit(number, acting, opposite, 0, 0);
        }
        /* Check if will finish in one's Kalah
         */
        if (amountOfStonesForTurn == (6 - number + 1)) {
            return makeMoveEndActiveKalah(number, acting, opposite, 0, 0);
        }
        /* Check if will finish in opposite pit in first round
         */
        if (amountOfStonesForTurn <= (6 - number + 1 + 6)) {
            return makeMoveEndOppositePit(number, acting, opposite, 0, 0);
        }
        /* Check if will make less than one full round
         */
        if (amountOfStonesForTurn <= 12) {
            return makeMoveEndActingPitNotFullRound (number, acting, opposite, 0, 0);
        }
        /* If will make full round several times
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
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActivePit(number, acting, opposite, times, initial);
            }
            if (left == (6 - number + 1)) {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActiveKalah(number, acting, opposite, times, initial);
            }
            if (left <= (6 - number + 1 + 6)) {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndOppositePit(number, acting, opposite, times, initial);
            }
            else {
                int initial = makeFullMove(number, acting, opposite, times);
                return makeMoveEndActingPitNotFullRound (number, acting, opposite, times, initial);
            }
        }
    }

    /* Check if end of the game
     */

    public boolean checkIfEndGame (Player actingPlayer, Player oppositePlayer) {

        if (getStonesCountInKalah(actingPlayer)>36) {
            return true;
        }
        if (getStonesCountInPits(oppositePlayer)==0) {
            actingPlayer.getKalahForPlayer().setStonesInKalah(getStonesCountInKalah(actingPlayer)+getStonesCountInPits(actingPlayer));
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkIfFirstIsTheWinner (Player first, Player second) {
        if (getStonesCountInKalah(first)>getStonesCountInKalah(second)) {
            return true;
        }
        else {
            return true;
        }
    }


    public int getStonesCountInPits (Player player) {
        int i = 0;
        for (Pit pit:player.getPitsForPlayer()) {
            i+=pit.getStonesInPit();
        }
        return i;
    }


    public int getStonesCountInKalah (Player player) {
        return player.getKalahForPlayer().getStonesInKalah();
    }

    public boolean makeMoveEndActivePit (int number, Player acting, Player opposite, int times, int initial) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();
        int amountOfStonesForTurn;
        Pit selected = pitsForActing.get(number - 1);
        if (initial==0) {
            amountOfStonesForTurn = selected.getStonesInPit();
            selected.setStonesInPit(times);
            for (int i = amountOfStonesForTurn, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(number + j));
            }
        }
        else {
            amountOfStonesForTurn = (initial - 13*times);
            selected.setStonesInPit(times);
            for (int i = amountOfStonesForTurn, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(number + j));
            }
        }
        if ((pitsForActing.get(number + amountOfStonesForTurn - 1).getStonesInPit() == 1) &&
                (pitsForOpposite.get(5 - (number + amountOfStonesForTurn - 1)).getStonesInPit() != 0)) {
            increaseStonesInKalahByAmount(kalahForActing, (1 + pitsForOpposite.get(5 - (number + amountOfStonesForTurn - 1)).getStonesInPit()));
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

    public boolean makeMoveEndActiveKalah (int number, Player acting, Player opposite, int times, int initial) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();
        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn;
        if (initial==0) {
            amountOfStonesForTurn = selected.getStonesInPit();
            selected.setStonesInPit(times);
            if (amountOfStonesForTurn!=1) {
                for (int i = (amountOfStonesForTurn-1), j = 0; i>0; i--, j++) {
                    increaseStonesInPitByOne(pitsForActing.get(number + j));
                }
            }
        }
        else {
            amountOfStonesForTurn = initial - 13*times;
            selected.setStonesInPit(times);
            if (amountOfStonesForTurn!=1) {
                for (int i = (amountOfStonesForTurn-1), j = 0; i>0; i--, j++) {
                    increaseStonesInPitByOne(pitsForActing.get(number + j));
                }
            }
        }

        increaseStonesInKalahByOne(kalahForActing);
        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean makeMoveEndOppositePit (int number, Player acting, Player opposite, int times, int initial) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();

        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn;
        if (initial==0) {
            amountOfStonesForTurn = selected.getStonesInPit();
            selected.setStonesInPit(times);
            int usedForActing = 0;
            for (int i = number, j = 1; i < 6; i++, j++) {
                increaseStonesInPitByOne(pitsForActing.get(i));
                usedForActing = j;
            }
            increaseStonesInKalahByOne(kalahForActing);
            usedForActing += 1;
            for (int i = (amountOfStonesForTurn - usedForActing), j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForOpposite.get(j));
            }
        }
        else {
            amountOfStonesForTurn = initial - 13*times;
            selected.setStonesInPit(times);
            int usedForActing = 0;
            for (int i = number, j = 1; i < 6; i++, j++) {
                increaseStonesInPitByOne(pitsForActing.get(i));
                usedForActing = j;
            }
            increaseStonesInKalahByOne(kalahForActing);
            usedForActing += 1;
            for (int i = (amountOfStonesForTurn - usedForActing), j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForOpposite.get(j));
            }
        }
        selected.setStonesInPit(times);

        if (checkIfEndGame(acting, opposite)) {
            return true;
        } else {
            acting.setInTurn(false);
            opposite.setInTurn(true);
            return false;
        }
    }
    public boolean makeMoveEndActingPitNotFullRound (int number, Player acting, Player opposite, int times, int initial) {
        List <Pit> pitsForActing = acting.getPitsForPlayer();
        List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
        Kalah kalahForActing = acting.getKalahForPlayer();
        Pit selected = pitsForActing.get(number - 1);
        int amountOfStonesForTurn;
        int leftForActing;
        int forFirstIteration;
        int forSecondIteration;
        if (initial==0) {
            amountOfStonesForTurn = selected.getStonesInPit();
            selected.setStonesInPit(times);
            increaseStonesInKalahByOne(kalahForActing);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForOpposite.get(j));
            }
            leftForActing = amountOfStonesForTurn - 6 - 1;
            forFirstIteration = 6 - number;
            forSecondIteration = leftForActing - forFirstIteration;
            for (int i = forFirstIteration, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(number + j));
            }
            for (int i = forSecondIteration, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(j));
            }
        }
        else {
            amountOfStonesForTurn = initial - 13*times;
            selected.setStonesInPit(times);
            increaseStonesInKalahByOne(kalahForActing);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForOpposite.get(j));
            }
            leftForActing = amountOfStonesForTurn - 6 - 1;
            forFirstIteration = 6 - number;
            forSecondIteration = leftForActing - forFirstIteration;
            for (int i = forFirstIteration, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(number + j));
            }
            for (int i = forSecondIteration, j = 0; i > 0; i--, j++) {
                increaseStonesInPitByOne(pitsForActing.get(j));
            }
        }
        if ((pitsForActing.get(forSecondIteration - 1).getStonesInPit() == 1) &&
                    (pitsForOpposite.get(5 - (forSecondIteration - 1)).getStonesInPit() != 0)) {
            increaseStonesInKalahByAmount(kalahForActing, (1 + pitsForOpposite.get(5 - (forSecondIteration - 1)).getStonesInPit()));
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
        public int makeFullMove (int number, Player acting, Player opposite, int times) {

            List <Pit> pitsForActing = acting.getPitsForPlayer();
            List <Pit> pitsForOpposite = opposite.getPitsForPlayer();
            Kalah kalahForActing = acting.getKalahForPlayer();
            Pit selected = pitsForActing.get(number - 1);
            int initial = selected.getStonesInPit();
            selected.setStonesInPit(0);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForActing.get(j).setStonesInPit(pitsForActing.get(j).getStonesInPit()+times);
            }
            increaseStonesInKalahByAmount(kalahForActing, times);
            for (int i = 6, j = 0; i > 0; i--, j++) {
                pitsForOpposite.get(j).setStonesInPit(pitsForOpposite.get(j).getStonesInPit()+times);
            }
            return initial;
        }


    public void increaseStonesInPitByOne(Pit pit) {
        pit.setStonesInPit(pit.getStonesInPit()+1);
    }

    public void increaseStonesInKalahByOne(Kalah kalah) {
        kalah.setStonesInKalah(kalah.getStonesInKalah()+1);
    }

    public void increaseStonesInKalahByAmount(Kalah kalah, int amount) {
        kalah.setStonesInKalah(kalah.getStonesInKalah()+amount);
    };
    }
