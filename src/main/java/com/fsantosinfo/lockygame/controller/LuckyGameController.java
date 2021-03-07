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
    public ModelAndView indexHome(){        
        
        Player loggedPlayer  = service.getLoggedPlayer();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("loggedPlayer", loggedPlayer);
        return modelAndView;
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
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-lucky-game");
       
        Player loggedPlayer  = service.getLoggedPlayer();
        
        LuckyGame luck = new LuckyGame();
        luck.setOwner(loggedPlayer);
        modelAndView.addObject("luckyGame", luck);
        modelAndView.addObject("loggedPlayer", loggedPlayer.getFirstName());
        
        return modelAndView;
    }


    @PostMapping("/addinggame")
    public ModelAndView creatingLuckyGame(@Valid @ModelAttribute LuckyGame luckyGame, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return new ModelAndView("new-lucky-game").addObject("loggedPlayer", luckyGame.getOwner().getFirstName());           
        }

        service.save(luckyGame);

        redirectAttributes.addFlashAttribute("message", "Game criado com sucesso");        
        return new ModelAndView("redirect:lucky-game/view/"+luckyGame.getId());      
    }


    @GetMapping("/lucky-game/view/{game_id}")
    public ModelAndView lockyGameView(@PathVariable Long game_id, RedirectAttributes redirectAttributes){              

        LuckyGame game = service.findById(game_id);      
        Player loggedPlayer = service.getLoggedPlayer();

        // This defence method will avoid the logged Player to access someone else's game
        if(game.getOwner().getEmail().equals(loggedPlayer.getEmail())){
            
            String playerName = loggedPlayer.getFirstName();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("lucky-game-view");
            modelAndView.addObject("loggedPlayer", playerName);
            modelAndView.addObject("oneGame", game);
            return modelAndView;
           
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Parece que você tentou acessar uma URL que não pertence a você. Use o Dashboard para uma navegação segura.");
            return new ModelAndView("redirect:/dashboard/player/"); // Offer a webpage with the errors in the top or a unique webpage of errors            
        }
    }


    @GetMapping("/lucky-game/edit/{id}")
    public ModelAndView editLuckyGame(@PathVariable Long id, RedirectAttributes redirectAttributes){
        final ModelAndView modelAndView = new ModelAndView();

        LuckyGame lucky = service.findById(id); 
        Player player = service.getLoggedPlayer();

        if(lucky.getOwner().getEmail().equals(player.getEmail())){
            modelAndView.setViewName("lucky-game-edit");
            modelAndView.addObject("luckyGame", lucky);
            return modelAndView;
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Parece que você tentou acessar uma URL que não pertence a você. Use o Dashboard para uma navegação segura.");
            return new ModelAndView("redirect:/dashboard/player/"); // Offer a webpage with the errors in the top or a unique webpage of errors            
        }
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
  
}
