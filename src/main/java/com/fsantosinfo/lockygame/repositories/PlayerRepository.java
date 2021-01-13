package com.fsantosinfo.lockygame.repositories;

import com.fsantosinfo.lockygame.model.entities.Player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    
}
