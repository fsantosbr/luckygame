package com.fsantosinfo.lockygame.controller;

import java.util.List;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping("/players")
    public ModelAndView players(){

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("players");
        
        modelAndView.addObject("allPlayers", service.findAll());
        return modelAndView;
        // note: This path is only for test. It won't exist on the production fase
    }

    @GetMapping("/lucky-game/enter/{id}")
    public String enterAGame(@PathVariable Long id, RedirectAttributes redirectAttributes){
        /*
        Checking here if the player is logged
        if yes, already save the information and return the view of the game
        */
        Long idRetrieved = 2L; // change later to the authentication
        LuckyGame lucky = service.gettingTheGame(id); // it must be here cause if the game does not exist, must return right here        
    
        // operação de insert
        service.insertPlayerAndGame(lucky, idRetrieved); // Change the Long for a Player when the authentication in a future release
       // check if there was an error here before proceding

        redirectAttributes.addFlashAttribute("message", "Tudo OK por aqui. Você está participando do jogo");
        return "redirect:/lucky-game/"+lucky.getId()+"/player/"+idRetrieved;
    }

    @GetMapping("/lucky-game/{gameId}/player/")
    public ModelAndView viewPlayerMatch(@PathVariable Long gameId, RedirectAttributes redirectAttributes){        
        Player player = service.getLoggedUser(); // change to not delivery all data
        
        final ModelAndView modelAndView = new ModelAndView();

        if (!service.alreadyPlayer(gameId, player.getId())){
            modelAndView.setViewName("player-match-view-notAllowed");
            modelAndView.addObject("player", player);            
        }
        else {           
            LuckyGame luckyGame = service.gettingTheGame(gameId);
            List<MyLuckyNumber> numbers = service.loadingPlayerNumbers(luckyGame, player);

            modelAndView.setViewName("player-match-view");
            modelAndView.addObject("luckyGame", luckyGame);
            modelAndView.addObject("player", player);
            modelAndView.addObject("numbers", numbers);
        }
        return modelAndView;
    }

    @GetMapping("/dashboard/player/{id}")
    public ModelAndView viewDashBoard(@PathVariable Long id){
        final ModelAndView modelAndView = new ModelAndView();
        
        /* here - a logic to check if a player is looged
        */

        Player player = service.findById(id);
        List<LuckyGame> games = player.getLuckyGames();
        List<LuckyGame> ownGames = player.getOwnerGames();

        modelAndView.setViewName("dashboard");
        modelAndView.addObject("player", player);
        modelAndView.addObject("games", games);
        modelAndView.addObject("ownGames", ownGames);

        return modelAndView;
    }

    
}
