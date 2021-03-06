package mykalah.mvc;

import mykalah.data.Game;
import mykalah.data.GameForm;
import mykalah.service.GameService;
import mykalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class MainController {

    private final PlayerService playerService;
    private final GameService gameService;


    @Autowired
    public MainController(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String startPage(Model model) {
        GameForm gameForm = new GameForm();
        model.addAttribute("playersForm", gameForm);
        return "index";
    }


    @RequestMapping(method = RequestMethod.POST)
    public RedirectView startGame(@ModelAttribute("playersForm") GameForm gameForm, RedirectAttributes redirectAttrs) {

        Game game = gameService.makeGame(gameForm.getFirstName(), gameForm.getSecondName());
        redirectAttrs.addFlashAttribute("makeMove", game);
        RedirectView gameBoard = new RedirectView();
        gameBoard.setContextRelative(true);
        gameBoard.setUrl("/makeMove");
        return gameBoard;
    }

    @RequestMapping(value = "/makeMove", method = RequestMethod.GET)
    public ModelAndView gameBoard(Model model) {
        Game game = (Game) model.asMap().get("makeMove");
        model.addAttribute("id", game.getId());
        return new ModelAndView("makeMove", "makeMove", game);
    }

    @RequestMapping(value = "/makeMove", method = RequestMethod.POST)
    public RedirectView makeMove(@RequestParam("id") long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        int i = Integer.parseInt(request.getParameter("numberOfPitForLastMove"));
        Game newGame = gameService.findOne(id);
        boolean result = gameService.makeMove(newGame.getId(), i);
        newGame = gameService.updateGame(newGame.getId(), i);

        if (playerService.findPlayerByName(newGame.getInitialFirstPlayer().getName()).isInTurn()) {
            newGame.setAsFirst(true);

        } else {
            newGame.setAsFirst(false);

        }
        RedirectView gameBoard = new RedirectView();
        gameBoard.setContextRelative(true);
        gameBoard.setUrl("/makeMove");
        RedirectView resultPage = new RedirectView();
        resultPage.setContextRelative(true);
        resultPage.setUrl("/result");
        if (result) {
            redirectAttributes.addFlashAttribute("makeMove", newGame);
            return resultPage;
        } else {
            redirectAttributes.addFlashAttribute("makeMove", newGame);
            return gameBoard;
        }
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView resultPage(Model model) {
        Game game = (Game) model.asMap().get("makeMove");
        String winner = game.getWinner();
        return new ModelAndView("result", "winner", winner);
    }
}




