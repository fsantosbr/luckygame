package com.fsantosinfo.lockygame.model.services;

import java.util.Optional;

import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class will help with some the business logic

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

	public Object findAll() {
		return repository.findAll();
	}

	public void save(Player player) {

        repository.save(player);
	}

	public Player findById(Long id) {        
        Optional<Player> optionalPlayer = repository.findById(id);
        return optionalPlayer.get();

	}
}
