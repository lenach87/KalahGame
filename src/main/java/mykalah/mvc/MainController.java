package mykalah.mvc;

import mykalah.data.*;

import mykalah.service.GameService;

import mykalah.service.MoveService;
import mykalah.service.PitService;
import mykalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


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
		model.addAttribute("playersForm", gameForm);
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public RedirectView startgame(@ModelAttribute("playersForm") Game gameForm, RedirectAttributes redirectAttrs) {

		Game game = gameService.makeGame(gameForm.getPlayer1(), gameForm.getPlayer2());
		game = gameService.updateGame(game, 0);
		redirectAttrs.addFlashAttribute("makeMove", game);
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/makeMove");
		return redirectView;
	}

	@RequestMapping(value = "/makeMove", method = RequestMethod.GET)
	public ModelAndView gameboard (Model model, RedirectAttributes redirectAttrs, HttpServletRequest request, Model map) {
		Game game = (Game) model.asMap().get("makeMove");
		map.addAttribute("id", game.getId());
		return new ModelAndView("makeMove", "makeMove", game);
	}

	@RequestMapping(value = "/makeMove", method = RequestMethod.POST)
	public RedirectView makeMove(@RequestParam("id") long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		int i = Integer.parseInt(request.getParameter("tempNumber"));
		Game newGame = gameService.findOne(id);
		boolean result = pitService.makeMove(newGame.getId(), i);
		newGame = gameService.findOne(gameService.updateGame(gameService.findOne(newGame.getId()), i).getId());
		if (playerService.findPlayerByName(newGame.getInitialPlayer1().getName()).isInTurn()) {
			newGame.setAsFirst(true);
		}
		else {
			newGame.setAsFirst(false);
		}
		// redirectAttributes.addFlashAttribute("makeMove", newGame);
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/makeMove");
		RedirectView redirectView2 = new RedirectView();
		redirectView2.setContextRelative(true);
		redirectView2.setUrl("/result");
		if (result) {
			if (pitService.getStonesCount(playerService.findPlayerByName(newGame.getInitialPlayer1().getName()))>pitService.getStonesCount(playerService.findPlayerByName(newGame.getInitialPlayer2().getName()))) {
				newGame.setWinner(newGame.getInitialPlayer1().getName());
				redirectAttributes.addFlashAttribute("makeMove", newGame);
				return redirectView2;
			}
			else {
				newGame.setWinner(newGame.getInitialPlayer2().getName());
				redirectAttributes.addFlashAttribute("makeMove", newGame);
				return redirectView2;
			}
		}
		else {
			redirectAttributes.addFlashAttribute("makeMove", newGame);
			return redirectView;
		}
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ModelAndView result(Model model, RedirectAttributes redirectAttrs) {
		Game game = (Game) model.asMap().get("makeMove");
		String winner = game.getWinner();
		return new ModelAndView("result", "winner", winner);
	}
}




