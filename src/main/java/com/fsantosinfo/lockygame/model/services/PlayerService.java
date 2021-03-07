package com.fsantosinfo.lockygame.model.services;

import java.util.List;
import java.util.Optional;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class will help with some business logic

@Service
public class PlayerService {

    @Autowired
	private PlayerRepository repository;
	
	@Autowired
	private LuckyGameService gameService;

	@Autowired
	private MyLuckyNumberService numberService;

	@Autowired
	private PlayerCredentialService credentialService;

	@Autowired
	private EligibleService eligibleService;


	public List<Player> findAll() {
		return repository.findAll();
	}

	public void save(Player player) {
        repository.save(player);
	}

	public Player findById(Long id) {        
        Optional<Player> optionalPlayer = repository.findById(id);
        return optionalPlayer.get();
	}

	public LuckyGame gettingTheGame(Long id) {
		return gameService.findById(id);
	}

	public void insertPlayerAndGame(LuckyGame lucky, Long player_id) {
		repository.insertPlayerAndGame(player_id, lucky.getId());
	}

	public void insertPlayerAndGame(LuckyGame lucky, Long player_id, Integer option) {
		repository.insertPlayerAndGame(player_id, lucky.getId());
		Player player = findById(player_id);
		eligibleService.addingTheAnswer(option, player, lucky);
    }

	public boolean alreadyPlayer(Long gameId, Long playerId) {		
		Boolean logicTest = false;
		List<Player> players = gameService.findById(gameId).getPlayers();		
		
		for (Player player : players){
			if (player.getId().equals(playerId)){
			logicTest = true;
			}
		}
		
		return logicTest;
	}


	public List<MyLuckyNumber> loadingPlayerNumbers(LuckyGame luckyGame, Player player) {
		List<MyLuckyNumber> numbers = numberService.findByGameAndPlayer(luckyGame, player);
		return numbers;

	}

	public Player getLoggedPlayer() {
		return credentialService.getLoggedPlayer();
	}

	public String getLoggedEmailOwner() {
		return credentialService.getLoggedEmailOwner();
	}

    
}
