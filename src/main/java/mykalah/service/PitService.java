package mykalah.service;

import mykalah.data.Kalah;
import mykalah.data.Pit;
import mykalah.data.Player;



public interface PitService {

    boolean makeMove (long gameId, int number);
    boolean checkIfEndGame (Player actingPlayer, Player oppositePlayer);
    boolean checkIfFirstIsTheWinner (Player first, Player second);
    int getStonesCountInPits (Player player);
    int getStonesCountInKalah (Player player);
    boolean makeMoveEndActivePit (int number, Player acting, Player opposite, int times, int initial);
    boolean makeMoveEndActiveKalah (int number, Player acting, Player opposite, int times, int initial);
    boolean makeMoveEndOppositePit (int number, Player acting, Player opposite, int times, int initial);
    boolean makeMoveEndActingPitNotFullRound (int number, Player acting, Player opposite, int times, int initial);
    int makeFullMove (int number, Player acting, Player opposite, int times);
    void increaseStonesInPitByOne(Pit pit);
    void increaseStonesInKalahByOne(Kalah kalah);
    void increaseStonesInKalahByAmount(Kalah kalah, int amount);
}
