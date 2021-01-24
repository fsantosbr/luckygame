package com.fsantosinfo.lockygame.model.repositories;

import java.util.List;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;
import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.model.entities.Player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLuckyNumberRepository extends JpaRepository<MyLuckyNumber, Long>{

    List<MyLuckyNumber> findByGameAndPlayer(LuckyGame luckyGame, Player player);
    
}
