package com.fsantosinfo.lockygame.model.services;

import com.fsantosinfo.lockygame.model.entities.Eligible;
import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.EligibleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EligibleService {
    
    @Autowired
    private EligibleRepository repository;

    public void save(Eligible eligible){
        repository.save(eligible);
    }

    public void addingTheAnswer(Integer option, Player player, LuckyGame lucky) {
        Eligible eligible = new Eligible(null, option, player, lucky);
        repository.save(eligible);
    }
}
