package com.fsantosinfo.lockygame.model.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fsantosinfo.lockygame.model.entities.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    
    @Modifying
    @Transactional
    @Query(value = "insert into TB_PLAYER_LUCKY_GAME (PLAYER_ID, LUCKYGAME_ID) values (:IDPLAYER, :IDGAME)", nativeQuery = true)
	void insertPlayerAndGame(@Param(value = "IDPLAYER") Long idPLayer, @Param(value = "IDGAME") Long idLuckyGame);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM TB_PLAYER_LUCKY_GAME u WHERE u.LUCKYGAME_ID = :IDGAME", nativeQuery = true)
    List<Long> findAllPlayers(@Param(value = "IDGAME") Long gameId);

    Optional<Player> findByEmail(String email);

}
