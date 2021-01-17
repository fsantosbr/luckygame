package com.fsantosinfo.lockygame.repositories;

import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLuckyNumberRepository extends JpaRepository<MyLuckyNumber, Long>{
    
}
