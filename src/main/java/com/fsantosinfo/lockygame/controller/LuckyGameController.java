package com.fsantosinfo.lockygame.controller;

import javax.validation.Valid;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.services.LuckyGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LuckyGameController {

    @Autowired
    private LuckyGameService service;

    @GetMapping("/")
    public String indexHome(){
        return "index";
    }

    @GetMapping("/games")
    public ModelAndView games(){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("games");
        
        modelAndView.addObject("allGames", service.findAll());
        return modelAndView;
        // note: This path is only for test. It won't exist on the production fase
    }

    @GetMapping("/new-lucky-game")
    public ModelAndView newLuckyGame(){
        /* 
            here, insert a condition to check if the user is authenticated.
            If not, redirect to logon, otherwise, getid
        */

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-lucky-game");
        
        Player loggedPlayer  = service.findPlayer(1L); // temporaly fake retrieve data serv.findById(1L);
        
        LuckyGame luck = new LuckyGame();
        luck.setOwner(loggedPlayer);
        modelAndView.addObject("luckyGame", luck);
        
        return modelAndView;
    }


    @PostMapping("/addinggame")
    public String createLuckyGame(@Valid @ModelAttribute LuckyGame luckyGame, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "new-lucky-game";
        }

        service.save(luckyGame);

        redirectAttributes.addFlashAttribute("message", "Game criado com sucesso");
        return "redirect:lucky-game/view/"+luckyGame.getId();
    }


    @GetMapping("/lucky-game/view/{id}")
    public ModelAndView lockyGameView(@PathVariable Long id){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lucky-game-view");
      
        LuckyGame lucky = service.findById(id);

        modelAndView.addObject("oneGame", lucky);
        return modelAndView;
    }


    @GetMapping("/lucky-game/edit/{id}")
    public ModelAndView editLuckyGame(@PathVariable Long id){
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("lucky-game-edit");
        modelAndView.addObject("luckyGame", service.findById(id));
        return modelAndView;
    }


    @RequestMapping(value = "/changinggame")
    public ModelAndView updatingLuckyGame(@Valid @ModelAttribute LuckyGame luckyGame, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return new ModelAndView("lucky-game-edit");
        }

        service.updateGameServ(luckyGame);

        redirectAttributes.addFlashAttribute("message", "Game editado com sucesso");
        return new ModelAndView("redirect:lucky-game/view/"+luckyGame.getId());
    }

    @GetMapping("/lucky-game/invite/{id}")
    public ModelAndView viewAnInvite(@PathVariable Long id){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("game-invite");
      
        LuckyGame lucky = service.findById(id);

        modelAndView.addObject("oneGame", lucky);
        return modelAndView;
    }
    
    @GetMapping("/lucky-game/entrar/{id}")
    public ModelAndView enrollingAGame(@PathVariable Long id){
        /*
        Checking here if the player is logged
        if yes, already save the information and return the view of the game
        */
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // change to view player match
        
        // operação de insert
        LuckyGame lucky = service.findById(id); // it must be here cause if the game does not exist, must return right here        
        service.insertPlayerAndGame(lucky, 2L); // Change the Long for a Player when the authentication in a future release
       
        return modelAndView;
    } 
  
}
