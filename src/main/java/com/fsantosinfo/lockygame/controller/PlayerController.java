package com.fsantosinfo.lockygame.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.entities.Quiz;
import com.fsantosinfo.lockygame.model.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    // Enroll the Player in a game (It will add the player already if the game does not have a quiz)
    @GetMapping("/enter/lucky-game/{game_id}")
    public ModelAndView enteringAGame(@PathVariable Long game_id, RedirectAttributes redirectAttributes){        

        Player loggedPlayer  = service.getLoggedPlayer();
        LuckyGame lucky = service.gettingTheGame(game_id); // it must be here cause if the game does not exist, must return right here        
   
        // A player can have more than 2 accounts, but the player cannot play a game with the same CPF
        List<String> cpfs = new ArrayList<>();
        lucky.getPlayers().stream().forEach((x) -> cpfs.add(x.getCpf()));
        Boolean alreadyInTheGame = cpfs.stream().anyMatch((x) -> x.equals(loggedPlayer.getCpf()));        
        if(!alreadyInTheGame){            
            cpfs.clear(); // Remove all data from memory

            // A Player is allowed to enroll a game if the limit data (closingDate) is higher than the now and if the game is published
            LocalDateTime currentLocalDateTime = LocalDateTime.now();            
            if (currentLocalDateTime.isAfter(lucky.getClosingDate()) || lucky.getPublished() == false){
                redirectAttributes.addFlashAttribute("message", "Não permitido. Data expirou ou o jogo foi pausado pelo criador.");
                return new ModelAndView("redirect:lucky-game/invite/"+lucky.getId());             
            }
            else{

                // If the game has quiz, will send the player to a quiz page
                if (!lucky.getQuizzes().isEmpty()){
                   
                    final ModelAndView modelAndView = new ModelAndView();                
                    Quiz quiz = new Quiz();
                    Quiz savedQuiz = lucky.getQuizzes().get(0); // For now, the App only has one quiz
                    quiz.setQuestion(savedQuiz.getQuestion());
                    quiz.setOption_1(savedQuiz.getOption_1());
                    quiz.setOption_2(savedQuiz.getOption_2());
                    quiz.setOption_3(savedQuiz.getOption_3());

                    modelAndView.setViewName("game-invite-quiz");
                    modelAndView.addObject("quiz", quiz);
                    modelAndView.addObject("loggedPlayer", loggedPlayer);
                    modelAndView.addObject("gameID", lucky.getId());               
                    return modelAndView;
                    
                }
                else {
                    service.insertPlayerAndGame(lucky, loggedPlayer.getId());
                    redirectAttributes.addFlashAttribute("message", "Tudo OK por aqui. Agora você está participando do jogo");
                    return new ModelAndView("redirect:/v/lucky-game/"+lucky.getId()+"/player/");
                }
            }

        }
        else{
            cpfs.clear(); // Remove all data from memory
            redirectAttributes.addFlashAttribute("message", "Você já estava participando do Jogo.");            
            return new ModelAndView("redirect:/v/lucky-game/"+lucky.getId()+"/player/");                      
        }
    }



    // Enroll the Player in a game that has a quiz
    @GetMapping("/enter/lucky-game/{game_id}/quiz")
    public ModelAndView enteringAGameWithQuiz(@PathVariable Long game_id, @RequestParam(value="option", defaultValue ="1") Integer option, RedirectAttributes redirectAttributes){

        Player loggedPlayer  = service.getLoggedPlayer();
        LuckyGame lucky = service.gettingTheGame(game_id); // it must be here cause if the game does not exist, must return right here        
   
        // A player can have more than 2 accounts, but the player cannot play a game with the same CPF
        List<String> cpfs = new ArrayList<>();
        lucky.getPlayers().stream().forEach((x) -> cpfs.add(x.getCpf()));
        Boolean alreadyInTheGame = cpfs.stream().anyMatch((x) -> x.equals(loggedPlayer.getCpf()));        
        if(!alreadyInTheGame){            
            
            cpfs.clear(); // Remove all data from memory

            // A Player is allowed to enroll a game if the limit data (closingDate) is higher than the now and if the game is published
            LocalDateTime currentLocalDateTime = LocalDateTime.now();            
            if (currentLocalDateTime.isAfter(lucky.getClosingDate()) || lucky.getPublished() == false){
                redirectAttributes.addFlashAttribute("message", "Não permitido. Data expirou ou o jogo foi pausado pelo criador.");
                //return "redirect:/lucky-game/invite/"+lucky.getId();
                return new ModelAndView("redirect:lucky-game/invite/"+lucky.getId());                
            }
            else{
                // If has quiz, it will calculate the the option is right
                if (!lucky.getQuizzes().isEmpty()){

                    service.insertPlayerAndGame(lucky, loggedPlayer.getId(), option);                    
                    redirectAttributes.addFlashAttribute("message", "Tudo OK por aqui. Agora você está participando do jogo");
                    return new ModelAndView("redirect:/v/lucky-game/"+lucky.getId()+"/player/");
                    
                }
                else {                    
                    service.insertPlayerAndGame(lucky, loggedPlayer.getId());
                    redirectAttributes.addFlashAttribute("message", "Tudo OK por aqui. Agora você está participando do jogo");
                    return new ModelAndView("redirect:/v/lucky-game/"+lucky.getId()+"/player/");
                }
                
            }

        }
        else{
            cpfs.clear(); // Remove all data from memory
            redirectAttributes.addFlashAttribute("message", "Você já estava participando do Jogo.");            
            return new ModelAndView("redirect:/v/lucky-game/"+lucky.getId()+"/player/");          
        }

    }


    @GetMapping("/v/lucky-game/{gameId}/player/")
    public ModelAndView viewPlayerMatch(@PathVariable Long gameId, RedirectAttributes redirectAttributes){        
        Player player = service.getLoggedPlayer(); // change to not delivery all data
        
        // Reduce this method to a clean code

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
