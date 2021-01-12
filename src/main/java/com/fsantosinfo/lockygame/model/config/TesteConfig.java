package com.fsantosinfo.lockygame.model.config;

import java.time.Instant;
import java.util.Arrays;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.repositories.LuckyGameRepository;

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

    @Override
    public void run(String... args) throws Exception {
        // this method will run every time this class is used - Only for test
        LuckyGame game1 = new LuckyGame(null, "Created On Repository 1", 2, Instant.now(), true, true, "void message", "fsantosinfo@gmail.com", "mudarsenha");
        LuckyGame game2 = new LuckyGame(null, "Created On Repository 2", 1, Instant.now(), true, true, "void message", "fsantosinfo@brq.com", "123321senha");
        
        luckyGameRepository.saveAll(Arrays.asList(game1, game2));
    }
    

}
