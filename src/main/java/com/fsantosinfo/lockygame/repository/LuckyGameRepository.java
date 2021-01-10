package com.fsantosinfo.lockygame.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fsantosinfo.lockygame.model.LuckyGame;
import com.fsantosinfo.lockygame.model.Player;

import org.springframework.stereotype.Repository;

@Repository
public class LuckyGameRepository {

    private List<LuckyGame> games;

    public LuckyGameRepository(){
        games = new ArrayList<>();
        
        // The following amount of code are related to just one test data
        games.add(new LuckyGame("Created On Repository 1", 2, 2L, Instant.now(), true, true, "void message", "fsantosinfo@gmail.com", "mudarsenha"));
        Player player = new Player(1L, "Paula", "pmolina@gmail.com", "123senha");
        player.addLuckNumber(56789);
        player.addLuckNumber(56787);
        player.addLuckNumber(56786);
        games.get(0).addPlayer(player);

        games.add(new LuckyGame("Created On Repository 2", 1, 2L, Instant.now(), true, true, "void message", "fsantosinfo@brq.com", "123321senha"));
        Player player2 = new Player(1L, "Fabio", "fsantos1987@gmail.com", "senhasenha");
        player2.addLuckNumber(76789);
        player2.addLuckNumber(76787);
        player2.addLuckNumber(76786);
        games.get(1).addPlayer(player2);
        
    }

    public List<LuckyGame> getAllLuckyGame(){
        return this.games;
    }

    public void addLuckyGame(final LuckyGame luckyGame){
        this.games.add(luckyGame);
    }

    public LuckyGame findById(Long id){
        List<LuckyGame> list = this.games;
       
        LuckyGame luc = new LuckyGame();

        for (LuckyGame lu : list){
            if (lu.getId() == id){
                luc = lu;
            }
        }
        return luc;
       
        // this implementation is not the right code. Problaby this will be moved to service classes
    }
    
}
