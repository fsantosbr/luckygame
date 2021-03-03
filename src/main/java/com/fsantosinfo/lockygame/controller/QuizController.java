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

    @GetMapping("/lucky-game/quiz/add/{game_id}")
    public ModelAndView newQuiz(@PathVariable Long game_id, RedirectAttributes redirectAttributes){        
        
        LuckyGame game = quizService.gettingTheGame(game_id); // insert here a defence method to check if the game exist
        Player loggedPlayer  = quizService.getLoggedPlayer();
        ModelAndView modelAndView = new ModelAndView();

        // This defence method will avoid the logged Player to access someone else's game/quiz
        if(game.getOwner().getEmail().equals(loggedPlayer.getEmail())){

            // If the game has a quiz, send it to the edit quiz page
            if (!game.getQuizzes().isEmpty()){
                return new ModelAndView("redirect:/lucky-game/quiz/edit/"+game_id);                

            }
            else {

                // if the game is not published, send it to the new quiz page
                if (!game.getPublished()){
                    Quiz quiz = new Quiz();
                    quiz.setLuckyGame(game);
                    modelAndView.setViewName("new-quiz");
                    modelAndView.addObject("quiz", quiz);
                    modelAndView.addObject("loggedPlayer", loggedPlayer.getFirstName());            
                    return modelAndView;
                }

                // If is published, a new quiz it's not allowed anymore
                else{
                    redirectAttributes.addFlashAttribute("message", "Não é mais possível adicionar um quiz ao jogo.");
                    return new ModelAndView("redirect:lucky-game/view/"+game.getId());       

                }
            }            
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Parece que você tentou acessar uma URL que não pertence a você. Use o Dashboard para uma navegação segura.");
            return new ModelAndView("redirect:/dashboard/player/"); // Offer a webpage with the errors in the top or a unique webpage of errors            
        }      
    }

    @PostMapping("/savingquiz")
    public ModelAndView savingQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return new ModelAndView("new-quiz").addObject("loggedPlayer", quiz.getLuckyGame().getOwner().getFirstName());           
        }

        quizService.save(quiz);

        redirectAttributes.addFlashAttribute("message", "Quiz criado com sucesso");
        return new ModelAndView("redirect:lucky-game/authorization/"+quiz.getLuckyGame().getId());       
    }

    @GetMapping("/lucky-game/quiz/edit/{game_id}") 
    public ModelAndView newQuiza(@PathVariable Long game_id, RedirectAttributes redirectAttributes){
        
        // insert here a defence to check if the game exist and if the logged player has permission to edit        
        // Insert here a defence to check if the game is already publish and no quiz inside. That's not allowed


        
        Player loggedPlayer  = quizService.getLoggedPlayer();
        LuckyGame game = quizService.gettingTheGame(game_id);
        

        // This defence method will avoid the logged Player to access someone else's game/quiz
        if(game.getOwner().getEmail().equals(loggedPlayer.getEmail())){

            // Here, we validade if there's a quiz to be changed. if so, we proced.
            if (!game.getQuizzes().isEmpty()){

                Quiz existingQuiz = game.getQuizzes().get(0); // For now, the game has just one quiz and no more.
                
                final ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("edit-quiz");
                modelAndView.addObject("quiz", existingQuiz);
                modelAndView.addObject("loggedPlayer", loggedPlayer.getFirstName());
                return modelAndView;

            }
            // Here, as game does not have a quiz, the game can't change anything and it returns the dashboard (because, we don't know if the game is published or not)
            // This validation will occur only if the player tries to access the url in the addrees bar
            else{                
                redirectAttributes.addFlashAttribute("message", "Não exite quiz criado ou não é permitido");
                return new ModelAndView("redirect:/dashboard/player/");            
            }
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Parece que você tentou acessar uma URL que não pertence a você. Use o Dashboard para uma navegação segura.");
            return new ModelAndView("redirect:/dashboard/player/"); // Offer a webpage with the errors in the top or a unique webpage of errors            
        }
      
    }

    @PostMapping("/changingquiz")
    public ModelAndView changingQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return new ModelAndView("new-quiz").addObject("loggedPlayer", quiz.getLuckyGame().getOwner().getFirstName());           
        }        

        quizService.update(quiz);

        if (quiz.getLuckyGame().getPublished()){
            redirectAttributes.addFlashAttribute("message", "Quiz ajustado com sucesso");
            return new ModelAndView("redirect:lucky-game/view/"+quiz.getLuckyGame().getId());            
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Quiz ajustado com sucesso");
            return new ModelAndView("redirect:lucky-game/authorization/"+quiz.getLuckyGame().getId());
        }

    }
    
    
}
