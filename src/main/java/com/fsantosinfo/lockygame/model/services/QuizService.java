package com.fsantosinfo.lockygame.model.services;

import java.util.Optional;

import com.fsantosinfo.lockygame.model.config.QuizRepository;
import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.entities.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository repository;
	
	
	@Autowired
    private PlayerService playerService;

    @Autowired
    private LuckyGameService gameService;

	// This method is used in the controller
    public LuckyGame gettingTheGame(Long game_id) {
		return gameService.findById(game_id);
    }

	// This method is used in the controller
	public Player getLoggedPlayer() {
		return playerService.getLoggedPlayer();
	}

	public void save(Quiz quiz) {
        repository.save(quiz);
	}

	public Quiz findById(Long quiz_id){
		Optional<Quiz> optQuiz = repository.findById(quiz_id);
		return optQuiz.get();
	}
	
	public void update(Quiz updatedQuiz) {
		Long quiz_id = updatedQuiz.getId();
		Quiz actualQuiz = findById(quiz_id);

		if (!updatedQuiz.getQuestion().equals(actualQuiz.getQuestion())){
			repository.updateQuestion(quiz_id, updatedQuiz.getQuestion());
		}

		if (!updatedQuiz.getOption_1().equals(actualQuiz.getOption_1())){
			repository.updateOption_1(quiz_id, updatedQuiz.getOption_1());
		}

		if (!updatedQuiz.getOption_2().equals(actualQuiz.getOption_2())){
			repository.updateOption_2(quiz_id, updatedQuiz.getOption_2());
		}

		if (!updatedQuiz.getOption_3().equals(actualQuiz.getOption_3())){
			repository.updateOption_3(quiz_id, updatedQuiz.getOption_3());
		}

		if (!updatedQuiz.getCorrectOption().equals(actualQuiz.getCorrectOption())){
			repository.updateCorrectOption(quiz_id, updatedQuiz.getCorrectOption());
		}

	}

}