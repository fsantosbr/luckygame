package com.fsantosinfo.lockygame.model.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.LuckyGameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class will help with some the business logic

@Service
public class LuckyGameService {

    @Autowired
    private LuckyGameRepository repository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MyLuckyNumberService numberService;

    public List<LuckyGame> findAll() {
        return repository.findAll();
    }

    public void save(LuckyGame luckyGame) {
        luckyGame.setMomentCreated(LocalDateTime.now());
        luckyGame.setPublished(false);
        luckyGame.setHasQuiz(false);
        luckyGame.setAlive(true); 
        repository.save(luckyGame);
    }

    public LuckyGame findById(Long id) {
        Optional<LuckyGame> optionalLucky = repository.findById(id);
        return optionalLucky.get();
    }

    public void updateGameServ(LuckyGame luckyGame) {
        // insert methods here to garantee the full transactions and a rollback in case of problems.
        
        Long id = luckyGame.getId();
        repository.updateTitleGame(id, luckyGame.getTitle());
        repository.updateCommunicateGame(id, luckyGame.getCommunicateAll());
        repository.updateNumWinnersGame(id, luckyGame.getNumWinners());       
        
    }
   

    private void gererateTheNumbers(Long id) {
        // Taking the players
        LuckyGame game = findById(id);
        List<Player> players = game.getPlayers();       
        
        // Defining the range of numbers
        int min = 311111;
        int max = 911111;

        // Creating a Set of numbers to make sure there's no repeated numbers        
        Set<Integer> setNumbers = new HashSet<>();        
        while(players.size() != setNumbers.size()){
            int number = (int)(Math.random() * (max - min + 1) + min);
            setNumbers.add(number);
        }

        // Passing the Set to an ordinary List       
        List<Integer> list = setNumbers.stream().collect(Collectors.toList());

        // Inserting the numbers      
        for (int i = 0; i < players.size(); i++){
            MyLuckyNumber myNumber = new MyLuckyNumber(null, list.get(i), players.get(i), game);
            numberService.save(myNumber);
        }        
    }


    public Player getLoggedPlayer() {
		return playerService.getLoggedPlayer();
    }
    
    public String getLoggedEmailOwner() {
		return playerService.getLoggedEmailOwner();
	}
}
