package controllers;

import mykalah.config.AppConfig;
import mykalah.data.Game;
import mykalah.mvc.MainController;
import mykalah.service.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import utils.GameBuilder;

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

  @InjectMocks
  MainController mainController;

  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;

  @Autowired
  private RequestMappingHandlerMapping handlerMapping;


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = standaloneSetup(mainController).build();
  }

  @Test
  public void getIndex() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk())
            .andExpect(view().name("index"))
    .andExpect(model().attribute("playersForm", Matchers.hasProperty("firstName", Matchers.nullValue())));
  }

  @Test
  public void postStartGame() throws Exception {

    Game gameForm = new Game();
    gameForm.setFirstName("first");
    gameForm.setSecondName("second");

    Game game;

    when(gameService.makeGame(gameForm.getFirstName(), gameForm.getSecondName())).thenReturn(game = new GameBuilder()
            .id(1)
            .firstName("first")
            .secondName("second")
            .asFirst(true)
            .numberOfPitForLastMove(0)
            .winner(null)
            .build());
    when(gameService.findOne(1)).thenReturn(game);

    mockMvc.perform(
            post("/").requestAttr("playersForm", gameForm))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/makeMove"));
  }



  @Test
  public void getGameBoard() throws Exception {

    Game game;
    when(gameService.findOne(1L)).thenReturn(game = new GameBuilder()
            .id(1L)
            .firstName("first")
            .secondName("second")
            .asFirst(true)
            .numberOfPitForLastMove(0)
            .winner(null)
            .build());

    mockMvc.perform(get("/makeMove").param("id", "1"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("id", 1));
  }


  @Test
  public void postMakeMove() throws Exception {

   mockMvc
        .perform(
            post("/makeMove").param("id", "1").param("numberOfPitForLastMove", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("makeMove"))
            .andExpect(model().attribute("makeMove", Matchers.hasProperty("numberOfPitForLastMove", Matchers.equalTo("5"))))
            .andExpect(model().attribute("makeMove", Matchers.hasProperty("asFirst", Matchers.equalTo("false"))));
  }

}
