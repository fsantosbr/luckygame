package com.fsantosinfo.lockygame.controller;

import com.fsantosinfo.lockygame.model.config.QuizRepository;
import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.entities.Quiz;
import com.fsantosinfo.lockygame.model.services.LuckyGameService;
import com.fsantosinfo.lockygame.model.services.PlayerService;

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


    public LuckyGame gettingTheGame(Long game_id) {
		return gameService.findById(game_id);
	}

	public Player getLoggedPlayer() {
		return playerService.getLoggedPlayer();
	}

	public void save(Quiz quiz) {
        repository.save(quiz);
	}





}
