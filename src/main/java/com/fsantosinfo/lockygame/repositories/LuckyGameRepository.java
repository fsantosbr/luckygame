package com.fsantosinfo.lockygame.repositories;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LuckyGameRepository extends JpaRepository<LuckyGame, Long>{
   

}
