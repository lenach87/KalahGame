package mykalah.mvc;

import mykalah.data.*;

import mykalah.service.GameService;
import mykalah.service.KalahService;
import mykalah.service.PitService;
import mykalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

	private final PlayerService playerService;
	private final PitService pitService;
	private final GameService gameService;


	@Autowired
	public MainController(PlayerService playerService, PitService pitService, GameService gameService) {
		this.playerService = playerService;
		this.pitService = pitService;
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView startpage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView start(
			@ModelAttribute("palyer1") Player player1,
			@ModelAttribute("palyer2") Player player2) {
		playerService.createNewPlayer(name1);
		playerService.createNewPlayer(name2);
		Game game = gameService.makeGame(name1, name2);
		long id = game.getId();
		return new ModelAndView("redirect/gameboard", "gameId", id);

	}

	@RequestMapping(value = "/gameboard", method = RequestMethod.GET)
	public ModelAndView gameboard (
			@RequestParam(value = "gameid", required = true) long gameid) {
		Player [] players = gameService.findOne(gameid).getPlayersOfGame();
		Player acting;
		Player opposite;
		if (players[0].isInTurn()) {
			acting = players[0];
			opposite = players[1];
		} else {
			acting = players[1];
			opposite = players[0];
		}
		return new ModelAndView("gameboard", "acting", acting.getName());
	}

	@RequestMapping(value = "/gameboard", method = RequestMethod.POST)
	public ModelAndView makeMove(
			@RequestParam(value = "gameid", required = true) long gameid,
			@RequestParam(value = "numberOfPit", required = true) int numberOfPit) {

		Player [] players = gameService.findOne(gameid).getPlayersOfGame();
		Player acting;
		Player opposite;
		if (players[0].isInTurn()) {
			acting = players[0];
			opposite = players[1];
		} else {
			acting = players[1];
			opposite = players[0];
		}
		boolean result = pitService.makeMove(gameid, numberOfPit);
		if (result) {
			if (pitService.getStonesCount(acting)>pitService.getStonesCount(opposite)) {
				return new ModelAndView("redirect/result", "winner", acting.getName());
			}
			else {
				return new ModelAndView("redirect/result", "winner", opposite.getName());
			}
		}
		return new ModelAndView ("redirect/gameboard");

	}
}




