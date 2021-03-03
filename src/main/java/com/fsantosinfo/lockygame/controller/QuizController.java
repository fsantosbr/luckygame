package com.fsantosinfo.lockygame.controller;

import javax.validation.Valid;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.entities.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/lucky-game/{game_id}")
    public ModelAndView newQuiz(@PathVariable Long game_id, RedirectAttributes redirectAttributes){
        
        // insert here a defence to check if the game exist and if the logged player has permission to edit        
        // Insert here a defence to check if the game is already publish and no quiz inside. That's not allowed

        
        Player loggedPlayer  = quizService.getLoggedPlayer();        
        Quiz quiz = new Quiz();
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-quiz");
        modelAndView.addObject("loggedPlayer", loggedPlayer.getFirstName());
        

        // Defence to check if the game already has a quiz, if so, load that quiz       
        LuckyGame game = quizService.gettingTheGame(game_id);
        if (!game.getQuizzes().isEmpty()){
            quiz = game.getQuizzes().get(0); //for now, the game only have one quiz.            
            
        }
        else{
            quiz.setLuckyGame(game);
        }
                
        modelAndView.addObject("quiz", quiz);
        modelAndView.addObject("gameTitle", game.getTitle()); //remove it in a future release        
        return modelAndView;        
    }

    @PostMapping("/savingquiz")
    public ModelAndView savingOrChanging(@Valid @ModelAttribute Quiz quiz, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return new ModelAndView("new-quiz").addObject("loggedPlayer", quiz.getLuckyGame().getOwner().getFirstName());           
        }
        // defence method to avoid another player trying to change someone else game
        // defence method to avoid invoking an edit method with an empty quiz

        quizService.save(quiz);

        redirectAttributes.addFlashAttribute("message", "Quiz ajustado com sucesso");
        return new ModelAndView("redirect:lucky-game/authorization/"+quiz.getLuckyGame().getId());       
    }
    
}
