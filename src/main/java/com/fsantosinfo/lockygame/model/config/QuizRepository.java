package com.fsantosinfo.lockygame.model.config;

import com.fsantosinfo.lockygame.model.entities.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
