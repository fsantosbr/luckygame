package com.fsantosinfo.lockygame.model.config;

import java.time.Instant;
import java.util.Arrays;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.LuckyGameRepository;
import com.fsantosinfo.lockygame.model.repositories.MyLuckyNumberRepository;
import com.fsantosinfo.lockygame.model.repositories.PlayerRepository;

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

        // Creating players - No integration needed
        Player p1 = new Player(null, "Fabio 1", "3333354841", "fsantosinfo1@gmail.com", "senha1", false);
        Player p2 = new Player(null, "Fabio 2", "3333354842", "fsantosinfo2@gmail.com", "senha2", false);
        Player p3 = new Player(null, "Fabio 3", "3333354843", "fsantosinfo3@gmail.com", "senha3", false);
        Player p4 = new Player(null, "Fabio 4", "3333354844", "fsantosinfo4@gmail.com", "senha4", false);
        Player p5 = new Player(null, "Fabio 5", "3333354845", "fsantosinfo5@gmail.com", "senha5", false);
        
        // Saving the players
        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // Creating some games with the owners (that are players)
        LuckyGame game1 = new LuckyGame(null, "Created On Repository 1", 2, Instant.now(), false, true, "entre no jogo 1", p1);
        LuckyGame game2 = new LuckyGame(null, "Created On Repository 2", 1, Instant.now(), false, true, "entre no jogo 2", p2);
        LuckyGame game3 = new LuckyGame(null, "Created On Repository 3", 3, Instant.now(), false, true, "entre no jogo 3", p3);
        LuckyGame game4 = new LuckyGame(null, "Created On Repository 4", 1, Instant.now(), false, true, "entre o jogo 4", p3);

        // Saving the games
        luckyGameRepository.saveAll(Arrays.asList(game1, game2, game3, game4));
        
        // Making the already registered players enroll games and then saving them.
        p1.getLuckyGames().add(game2);
        p1.getLuckyGames().add(game1);
        p2.getLuckyGames().add(game2);
        p3.getLuckyGames().add(game2);
        p4.getLuckyGames().add(game2);
        p4.getLuckyGames().add(game3);
        p5.getLuckyGames().add(game3);
        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // Adding numbers to some players
        MyLuckyNumber n1 = new MyLuckyNumber(null, 1234561, p1, game2);
        MyLuckyNumber n2 = new MyLuckyNumber(null, 1234562, p1, game2);
        MyLuckyNumber n3 = new MyLuckyNumber(null, 1234563, p3, game2);
        MyLuckyNumber n4 = new MyLuckyNumber(null, 1234564, p4, game3);
        MyLuckyNumber n5 = new MyLuckyNumber(null, 1234565, p4, game3);
        MyLuckyNumber n6 = new MyLuckyNumber(null, 1234566, p4, game3);
        MyLuckyNumber n7 = new MyLuckyNumber(null, 1234567, p4, game2);
        MyLuckyNumber n8 = new MyLuckyNumber(null, 1234568, p5, game3);
        myLuckyNumberRepository.saveAll(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8));
        
        /*
        Não subir números sem mencionar a relação com o player e o jogo.
        Se subir, vai precisar usar query para editar os números e mencionar o player e o jogo.
        */               

    }
    
}
