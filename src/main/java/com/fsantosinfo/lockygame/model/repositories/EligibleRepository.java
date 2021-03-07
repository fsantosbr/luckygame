package com.fsantosinfo.lockygame.model.repositories;

import com.fsantosinfo.lockygame.model.entities.Eligible;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EligibleRepository extends JpaRepository<Eligible, Long>{
    
}
