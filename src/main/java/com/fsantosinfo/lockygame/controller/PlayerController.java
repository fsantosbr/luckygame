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

    @GetMapping("/enter/lucky-game/{id}")
    public String enterAGame(@PathVariable Long id, RedirectAttributes redirectAttributes){        

        Player loggedPlayer  = service.getLoggedPlayer();
        LuckyGame lucky = service.gettingTheGame(id); // it must be here cause if the game does not exist, must return right here        
        List<Player> players = lucky.getPlayers();
        Boolean alreadyInTheGame = players.stream().anyMatch((x) -> x.equals(loggedPlayer));
        
        if(!alreadyInTheGame){
            service.insertPlayerAndGame(lucky, loggedPlayer.getId());            
            redirectAttributes.addFlashAttribute("message", "Tudo OK por aqui. Agora você está participando do jogo");
            return "redirect:/lucky-game/"+lucky.getId()+"/player/";
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Você já estava participando do Jogo.");
            return "redirect:/lucky-game/"+lucky.getId()+"/player/";                       
        }
    }


    @GetMapping("/lucky-game/{gameId}/player/")
    public ModelAndView viewPlayerMatch(@PathVariable Long gameId, RedirectAttributes redirectAttributes){        
        Player player = service.getLoggedPlayer(); // change to not delivery all data
        
        final ModelAndView modelAndView = new ModelAndView();

        if (!service.alreadyPlayer(gameId, player.getId())){
            redirectAttributes.addFlashAttribute("message", "Parece que você tentou acessar uma URL que não pertence a você. Use o Dashboard para uma navegação segura.");
            return new ModelAndView("redirect:/dashboard/player/");
        }
        else {           
            LuckyGame luckyGame = service.gettingTheGame(gameId);
            List<MyLuckyNumber> numbers = service.loadingPlayerNumbers(luckyGame, player);

            modelAndView.setViewName("player-match-view");
            modelAndView.addObject("luckyGame", luckyGame);
            modelAndView.addObject("player", player);
            modelAndView.addObject("numbers", numbers);

            return modelAndView;
        }        
    }


    @GetMapping("/dashboard/player/")
    public ModelAndView viewDashBoard(){
        final ModelAndView modelAndView = new ModelAndView();
        
        Player player = service.getLoggedPlayer();
        List<LuckyGame> games = player.getLuckyGames();
        List<LuckyGame> ownGames = player.getOwnerGames();

        modelAndView.setViewName("dashboard");
        modelAndView.addObject("player", player);
        modelAndView.addObject("games", games);
        modelAndView.addObject("ownGames", ownGames);

        return modelAndView;
    }    
}
