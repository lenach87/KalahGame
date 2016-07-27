package app.controllers;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import mykalah.config.AppConfig;
import mykalah.data.Game;
import mykalah.data.Kalah;
import mykalah.data.Pit;
import mykalah.data.Player;
import mykalah.mvc.MainController;
import mykalah.service.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utils.GameBuilder;
import utils.KalahBuilder;
import utils.PitBuilder;
import utils.PlayerBuilder;

import java.util.*;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class MainControllerTest {

  MockMvc mockMvc;

  @Mock
  GameService gameService;

  @Mock
  PlayerService playerService;

  @Mock
  PitService pitService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = standaloneSetup(new MainController(playerService,pitService,gameService)).build();
  }

  @Test
  public void getIndex() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk())
            .andExpect(view().name("index"))
    .andExpect(model().attribute("playersForm", Matchers.hasProperty("firstName", Matchers.nullValue())));
  }

  @Test
  public void postStartGame() throws Exception {

    List <Pit> pitsForActing = new ArrayList<>();
    for (int i=1; i<7; i++) {
      Pit added = new PitBuilder()
              .id(i)
              .stonesInPit(6)
              .build();
      pitsForActing.add(added);
    }

    List <Pit> pitsForOpposite = new ArrayList<>();
    for (int i=7; i<13; i++) {
      Pit added = new PitBuilder()
              .id(i)
              .stonesInPit(6)
              .build();
      pitsForOpposite.add(added);
    }

    Kalah kalahForActing = new KalahBuilder()
            .id(1)
            .stonesInKalah(0)
            .build();

    Kalah kalahForOpposite = new KalahBuilder()
            .id(2)
            .stonesInKalah(0)
            .build();

    Player actingPlayer = new PlayerBuilder()
            .id(1)
            .kalahForPlayer(kalahForActing)
            .pitsForPlayer(pitsForActing)
            .inTurn(true)
            .name("first")
            .build();

    Player oppositePlayer = new PlayerBuilder()
            .id(2)
            .kalahForPlayer(kalahForOpposite)
            .pitsForPlayer(pitsForOpposite)
            .inTurn(false)
            .name("second")
            .build();

    Game gameForm = new Game();
    gameForm.setFirstName("first");
    gameForm.setSecondName("second");

    Game game;

   when(gameService.makeGame(gameForm.getFirstName(), gameForm.getSecondName())).thenReturn(game = new GameBuilder()
            .id(1)
            .firstName("first")
            .secondName("second")
            .initialFirstPlayer(actingPlayer)
            .initialSecondPlayer(oppositePlayer)
            .asFirst(true)
            .numberOfPitForLastMove(0)
            .winner(null)
            .build());
    when(gameService.findOne(1)).thenReturn(game);

    mockMvc.perform(
                    post("/").requestAttr("playersForm", gameForm))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/makeMove"))
            .andExpect(MockMvcResultMatchers.flash().attribute("makeMove", Matchers.hasProperty("firstName", Matchers.equalTo("first"))));

  }


  @Test
  public void postMakeMove() throws Exception {


//    List <Pit> pitsForActing = new ArrayList<>();
//    for (int i=1; i<7; i++) {
//      Pit added = new PitBuilder()
//              .id(i)
//              .stonesInPit(6)
//              .build();
//      pitsForActing.add(added);
//    }
//
//    List <Pit> pitsForOpposite = new ArrayList<>();
//    for (int i=7; i<13; i++) {
//      Pit added = new PitBuilder()
//              .id(i)
//              .stonesInPit(6)
//              .build();
//      pitsForOpposite.add(added);
//    }
//
//    Kalah kalahForActing = new KalahBuilder()
//            .id(1)
//            .stonesInKalah(0)
//            .build();
//
//    Kalah kalahForOpposite = new KalahBuilder()
//            .id(2)
//            .stonesInKalah(0)
//            .build();
//
//    Player actingPlayer = new PlayerBuilder()
//            .id(1)
//            .kalahForPlayer(kalahForActing)
//            .pitsForPlayer(pitsForActing)
//            .inTurn(true)
//            .name("first")
//            .build();
//
//    Player oppositePlayer = new PlayerBuilder()
//            .id(2)
//            .kalahForPlayer(kalahForOpposite)
//            .pitsForPlayer(pitsForOpposite)
//            .inTurn(false)
//            .name("second")
//            .build();
//
//    Game game = new GameBuilder()
//            .id(1)
//            .firstName("first")
//            .secondName("second")
//            .initialFirstPlayer(actingPlayer)
//            .initialSecondPlayer(oppositePlayer)
//            .asFirst(true)
//            .numberOfPitForLastMove(0)
//            .winner(null)
//            .build();
//
//    when(gameService.findOne(1)).thenReturn(game);
   // when(playerService.findPlayerByName(gameService.findOne(1).getFirstName())).thenReturn(actingPlayer);
   // when(playerService.findPlayerByName(gameService.findOne(1).getSecondName())).thenReturn(oppositePlayer);


    mockMvc
        .perform(
            post("/makeMove").param("id", "1").param("numberOfPitForLastMove", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("makeMove"))
            .andExpect(model().attribute("makeMove", Matchers.hasProperty("numberOfPitForLastMove", Matchers.equalTo("5"))))
            .andExpect(model().attribute("makeMove", Matchers.hasProperty("asFirst", Matchers.equalTo("false"))));


  //  verify(pitService).makeMove(1, 5);
  //  Assert.assertEquals(actingPlayer.getPitsForPlayer().get(4).getStonesInPit(),0);

  }

}
