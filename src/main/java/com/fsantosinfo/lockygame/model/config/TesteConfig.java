package com.fsantosinfo.lockygame.model.config;

import java.time.Instant;
import java.util.Arrays;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.repositories.LuckyGameRepository;
import com.fsantosinfo.lockygame.repositories.MyLuckyNumberRepository;
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

    @Autowired
    private MyLuckyNumberRepository myLuckyNumberRepository;

    @Override
    public void run(String... args) throws Exception {
        // this method will run every time this class is used - Only for test

        Player p1 = new Player(null, "Fabio 1", "354841", "fsantosinfo@gmail.com", "senha", false);
        Player p2 = new Player(null, "Fabio 2", "354842", "fsantosinfo@gmail.com", "senha", false);
        Player p3 = new Player(null, "Fabio 3", "354843", "fsantosinfo@gmail.com", "senha", false);
        Player p4 = new Player(null, "Fabio 4", "354844", "fsantosinfo@gmail.com", "senha", false);
        Player p5 = new Player(null, "Fabio 5", "354845", "fsantosinfo@gmail.com", "senha", false);
        
        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        LuckyGame game1 = new LuckyGame(null, "Created On Repository 1", 2, Instant.now(), true, true, "void message", p1);
        LuckyGame game2 = new LuckyGame(null, "Created On Repository 2", 1, Instant.now(), true, true, null, p2);
                
        luckyGameRepository.saveAll(Arrays.asList(game1, game2));
        

        p1.getLuckyGames().add(game2);
        p1.getLuckyGames().add(game1);
        p2.getLuckyGames().add(game2);
        p3.getLuckyGames().add(game2);
        p4.getLuckyGames().add(game1);
        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4));


        MyLuckyNumber n1 = new MyLuckyNumber(null, 1234561, p1);
        MyLuckyNumber n2 = new MyLuckyNumber(null, 1234562, p2);
        MyLuckyNumber n3 = new MyLuckyNumber(null, 1234563, p3);
        MyLuckyNumber n4 = new MyLuckyNumber(null, 1234564, p4);

        myLuckyNumberRepository.saveAll(Arrays.asList(n1, n2, n3, n4));

        /*
        Não subir números sem mencionar a relação com o player.
        Se subir, vai precisar usar query para editar os números e mencionar o player.
        */               

    }
    
}
