package com.fsantosinfo.lockygame.model.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.repositories.LuckyGameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class will help with some the business logic

@Service
public class LuckyGameService {

        @Autowired
        private LuckyGameRepository repository;

        @Autowired
        private PlayerService playerService;

        public List<LuckyGame> findAll() {
                return repository.findAll();
        }

        public void save(LuckyGame luckyGame) {
                luckyGame.setMomentCreated(Instant.now());
                luckyGame.setOpen(true);
                luckyGame.setAlive(true);
                if (luckyGame.getNumWinners() == null) {
                        luckyGame.setNumWinners(1);
                }
                repository.save(luckyGame);
        }

        public LuckyGame findById(Long id) {
                Optional<LuckyGame> optionalLucky = repository.findById(id);
                return optionalLucky.get();
        }

        public void updateGameServ(LuckyGame luckyGame) {
                Long id = luckyGame.getId();
                repository.updateTitleGame(id, luckyGame.getTitle());
                repository.updateCommunicateGame(id, luckyGame.getCommunicateAll());

                if (luckyGame.getNumWinners() == null) {
                        luckyGame.setNumWinners(1);
                }

                repository.updateNumWinnersGame(id, luckyGame.getNumWinners());
        }

	public Player findPlayer(long id) {
                return playerService.findById(id);                
	}
}
