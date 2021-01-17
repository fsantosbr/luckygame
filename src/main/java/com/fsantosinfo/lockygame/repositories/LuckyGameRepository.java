package com.fsantosinfo.lockygame.repositories;

import com.fsantosinfo.lockygame.model.entities.LuckyGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LuckyGameRepository extends JpaRepository<LuckyGame, Long>{
    
    @Modifying
    @Query(value = "update lucky_game u set u.TITLE = :TITLE where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateTitleGame(@Param(value = "ID") Long id, @Param(value = "TITLE") String title);

    @Modifying
    @Query(value = "update lucky_game u set u.COMMUNICATE_ALL = :COMMUNICATE_ALL where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateCommunicateGame(@Param(value = "ID") Long id, @Param(value = "COMMUNICATE_ALL") String communicateAll);

    @Modifying
    @Query(value = "update lucky_game u set u.NUM_WINNERS = :NUM_WINNERS where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateNumWinnersGame(@Param(value = "ID") Long id, @Param(value = "NUM_WINNERS") Integer numWinners);

    @Modifying
    @Transactional
    @Query(value = "insert into TB_PLAYER_LUCKY_GAME (PLAYER_ID, LUCKYGAME_ID) values (:IDPLAYER, :IDGAME)", nativeQuery = true)
	void insertPlayerAndGame(@Param(value = "IDPLAYER") Long idPLayer, @Param(value = "IDGAME") Long idLuckyGame);
}
