package mykalah.mvc;

import mykalah.data.*;

import mykalah.service.GameService;
import mykalah.service.KalahService;
import mykalah.service.PitService;
import mykalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String startpage(Model model) {
		Game gameForm = new Game();
		model.addAttribute("gameForm", gameForm);
		return "index";
	}

	@RequestMapping(value = "/startgame", method = RequestMethod.POST)
	public ModelAndView startgame(
			@ModelAttribute("gameForm") Game gameForm, BindingResult result,
			ModelMap model, RedirectAttributes redirect) {
		model.addAttribute("player1", gameForm.getPlayer1());
		model.addAttribute("player2", gameForm.getPlayer2());

		//if (gameService.makeGame(gameForm.getPlayersOfGame()[0], gameForm.getPlayersOfGame()[1])!=null) {
		Game newgame = gameService.makeGame(gameForm.getPlayer1(), gameForm.getPlayer2());
		//Player [] players = new Player [] {playerService.findPlayerByName(gameForm.getPlayer1()), playerService.findPlayerByName(gameForm.getPlayer2())};
		redirect.addFlashAttribute("globalMessage", "Game added successfully");
		return new ModelAndView("gameboard", "gameForm", newgame);
	//	}
	//	else {
	//		result.rejectValue("player2", "player2", "Name already in use");
	//		return new ModelAndView("index");
	//	}
	}

	@RequestMapping(value = "/gameboard", method = RequestMethod.GET)
	public ModelAndView viewboard (
			@ModelAttribute("gameForm") Game game) {
		ModelAndView mv = new ModelAndView("redirect:/gameboard");
		Player first = playerService.findPlayerByName(game.getPlayer1());
		Player second = playerService.findPlayerByName(game.getPlayer2());
		Player acting;
		Player opposite;
		if (first.isInTurn()) {
			acting = first;
			opposite = second;
		} else {
			acting = second;
			opposite = first;
		}
		Player [] gameplayers = new Player [] {acting, opposite};
		mv.addObject("game", game);
		mv.addObject("players", gameplayers);
        return mv;
	}

	@RequestMapping(value = "/gameboard", method = RequestMethod.POST)
	public ModelAndView makeMove(
			@ModelAttribute("players") Player [] gameplayers,
            @ModelAttribute("game") Game game,
			@RequestParam(value = "numberOfPit", required = true) int numberOfPit) {

		final Player first = gameplayers[0];
		final Player second =gameplayers[1];
		Player acting;
		Player opposite;
		if (first.isInTurn()) {
			acting = first;
			opposite = second;
		} else {
			acting = second;
			opposite = first;
		}
		boolean result = pitService.makeMove(game.getId(), numberOfPit);
		if (result) {
			if (pitService.getStonesCount(acting)>pitService.getStonesCount(opposite)) {
				return new ModelAndView("redirect:/result", "winner", acting.getName());
			}
			else {
				return new ModelAndView("redirect:/result", "winner", opposite.getName());
			}
		}
		return new ModelAndView ("redirect:/gameboard");

	}
}




