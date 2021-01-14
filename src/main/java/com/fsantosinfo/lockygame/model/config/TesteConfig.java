package com.fsantosinfo.lockygame.model.config;

import java.time.Instant;
import java.util.Arrays;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.repositories.LuckyGameRepository;
import com.fsantosinfo.lockygame.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {
    // This class will helps to make the database seeding
    // This class of configutation will (only) run in the test profile

    @Autowired
    private LuckyGameRepository luckyGameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {
        // this method will run every time this class is used - Only for test
        LuckyGame game1 = new LuckyGame(null, "Created On Repository 1", 2, Instant.now(), true, true, "void message", "fsantosinfo@gmail.com", "mudarsenha");
        LuckyGame game2 = new LuckyGame(null, "Created On Repository 2", 1, Instant.now(), true, true, null, "fsantosinfo@brq.com", "123321senha");
        
        luckyGameRepository.saveAll(Arrays.asList(game1, game2));
        

        Player p1 = new Player(null, "Fabio 1 player", "fsantosinfo@gmail.com", "senha");
        Player p2 = new Player(null, "Fabio 2 player", "fsantosinfo@gmail.com", "senha");
        Player p3 = new Player(null, "Fabio 3 player", "fsantosinfo@gmail.com", "senha");
        Player p4 = new Player(null, "Fabio 4 player", "fsantosinfo@gmail.com", "senha", game2);
        Player p5 = new Player(null, "Fabio 5 player", "fsantosinfo@gmail.com", "senha", game2);

        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
        
        

        


    }
    

}
