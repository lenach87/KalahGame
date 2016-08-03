package service;

import mykalah.config.AppConfig;
import mykalah.data.Game;
import mykalah.data.GameRepository;
import mykalah.data.Player;
import mykalah.data.PlayerRepository;
import mykalah.service.GameService;
import mykalah.service.GameServiceImpl;
import mykalah.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utils.GameBuilder;
import utils.PlayerBuilder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@Transactional
public class GameServiceImplTest {

    @Mock
    GameRepository gameRepository;
    @Mock
    PlayerRepository playerRepository;
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private GameService gameService = new GameServiceImpl();

    private Player actingPlayer;
    private Player oppositePlayer;
    private Game game;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMakeMoveEndActivePit() {
        actingPlayer = new PlayerBuilder()

                .kalahForPlayer(1)
                .pitsForPlayer(new int[]{1, 7, 7, 7, 7, 7})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(1)
                .pitsForPlayer(new int[]{6, 0, 7, 7, 7, 7})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 1);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 8);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 7);
        assertTrue(actingPlayer.getKalahForPlayer() == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 0);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 7);
        assertTrue(!actingPlayer.isInTurn());
    }

    @Test
    public void testMakeMoveEndActiveKalah() {
        actingPlayer = new PlayerBuilder()

                .kalahForPlayer(0)
                .pitsForPlayer(new int[]{6, 6, 6, 6, 6, 6})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(0)
                .pitsForPlayer(new int[]{6, 6, 6, 6, 6, 6})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 1);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 7);
        assertTrue(actingPlayer.getKalahForPlayer() == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 6);
        assertTrue(oppositePlayer.getKalahForPlayer() == 0);
        assertTrue(actingPlayer.isInTurn());
    }

    @Test
    public void testMakeMoveEndOppositePit() {
        actingPlayer = new PlayerBuilder()

                .kalahForPlayer(0)
                .pitsForPlayer(new int[]{6, 6, 6, 6, 6, 6})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(0)
                .pitsForPlayer(new int[]{6, 6, 6, 6, 6, 6})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 4);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 6);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 6);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 6);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 7);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 7);
        assertTrue(actingPlayer.getKalahForPlayer() == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 7);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 6);
        assertTrue(oppositePlayer.getKalahForPlayer() == 0);
        assertTrue(!actingPlayer.isInTurn());
    }

    @Test
    public void testMakeMoveEndActivePitNotFullRound() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(3)
                .pitsForPlayer(new int[]{9, 9, 2, 10, 9, 0})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(3)
                .pitsForPlayer(new int[]{9, 8, 0, 8, 1, 1})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 4);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 10);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 9);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 2);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 10);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 1);
        assertTrue(actingPlayer.getKalahForPlayer() == 4);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 10);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 9);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 9);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 2);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 2);
        assertTrue(oppositePlayer.getKalahForPlayer() == 3);
        assertTrue(!actingPlayer.isInTurn());

    }

    @Test
    public void testMakeMoveCaptureOppositeStones() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(5)
                .pitsForPlayer(new int[]{11, 10, 2, 0, 0, 2})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(3)
                .pitsForPlayer(new int[]{11, 10, 1, 11, 3, 3})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 3);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 11);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 10);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 1);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 2);
        assertTrue(actingPlayer.getKalahForPlayer() == 16);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 11);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 0);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 11);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 3);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 3);
        assertTrue(oppositePlayer.getKalahForPlayer() == 3);
        assertTrue(!actingPlayer.isInTurn());

    }

    @Test
    public void testMakeMoveFullRound() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(17)
                .pitsForPlayer(new int[]{13, 1, 2, 3, 2, 4})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(5)
                .pitsForPlayer(new int[]{0, 2, 3, 13, 5, 2})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 1);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 0);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 2);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 3);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 4);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 3);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 5);
        assertTrue(actingPlayer.getKalahForPlayer() == 22);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 3);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 4);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 14);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 6);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 0);
        assertTrue(oppositePlayer.getKalahForPlayer() == 5);
        assertTrue(!actingPlayer.isInTurn());
    }

    @Test
    public void testMakeMoveMoreThanFullRound() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(22)
                .pitsForPlayer(new int[]{0, 2, 3, 4, 3, 5})
                .inTurn(false)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(5)
                .pitsForPlayer(new int[]{1, 3, 4, 14, 6, 0})
                .inTurn(true)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(false)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 4);
        assertTrue(actingPlayer.getPitsForPlayer()[0] == 1);
        assertTrue(actingPlayer.getPitsForPlayer()[1] == 3);
        assertTrue(actingPlayer.getPitsForPlayer()[2] == 4);
        assertTrue(actingPlayer.getPitsForPlayer()[3] == 5);
        assertTrue(actingPlayer.getPitsForPlayer()[4] == 4);
        assertTrue(actingPlayer.getPitsForPlayer()[5] == 6);
        assertTrue(actingPlayer.getKalahForPlayer() == 22);
        assertTrue(oppositePlayer.getPitsForPlayer()[0] == 2);
        assertTrue(oppositePlayer.getPitsForPlayer()[1] == 4);
        assertTrue(oppositePlayer.getPitsForPlayer()[2] == 5);
        assertTrue(oppositePlayer.getPitsForPlayer()[3] == 1);
        assertTrue(oppositePlayer.getPitsForPlayer()[4] == 8);
        assertTrue(oppositePlayer.getPitsForPlayer()[5] == 1);
        assertTrue(oppositePlayer.getKalahForPlayer() == 6);
        assertTrue(actingPlayer.isInTurn());
    }

    @Test
    public void testEndGameWhenPlayerHasNoStones() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(20)
                .pitsForPlayer(new int[]{10, 9, 1, 0, 10, 2})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(7)
                .pitsForPlayer(new int[]{0, 0, 13, 0, 0, 0})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 3);
        assertTrue(actingPlayer.getKalahForPlayer() == 65);
        assertTrue(game.getWinner() == "first");
    }

    @Test
    public void testEndGameWhenPlayerHasMoreThan36StonesInKalah() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(36)
                .pitsForPlayer(new int[]{10, 9, 1, 0, 10, 2})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(7)
                .pitsForPlayer(new int[]{0, 0, 13, 0, 0, 0})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 6);
        assertTrue(actingPlayer.getKalahForPlayer() == 37);
        assertTrue(game.getWinner() == "first");
    }

    @Test
    public void testEndGameNoWinner() {
        actingPlayer = new PlayerBuilder()
                .kalahForPlayer(34)
                .pitsForPlayer(new int[]{0, 0, 0, 0, 1, 0})
                .inTurn(true)
                .name("first")
                .build();
        oppositePlayer = new PlayerBuilder()

                .kalahForPlayer(36)
                .pitsForPlayer(new int[]{1, 0, 0, 0, 0, 0})
                .inTurn(false)
                .name("second")
                .build();

        when(gameRepository.findOne(anyLong())).thenReturn(game = new GameBuilder()
                .id(1)
                .initialFirstPlayer(actingPlayer)
                .initialSecondPlayer(oppositePlayer)
                .asFirst(true)
                .numberOfPitForLastMove(0)
                .winner(null)
                .build());

        gameService.makeMove(game.getId(), 5);
        assertTrue(actingPlayer.getKalahForPlayer() == 36);
        assertTrue(game.getWinner() == "none");
    }
}