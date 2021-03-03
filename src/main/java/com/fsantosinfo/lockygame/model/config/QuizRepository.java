package com.fsantosinfo.lockygame.model.config;

import javax.transaction.Transactional;

import com.fsantosinfo.lockygame.model.entities.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

	
    @Modifying
    @Query(value = "update quiz u set u.QUESTION = :QUESTION where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateQuestion(@Param(value = "ID") Long quiz_id, @Param(value = "QUESTION") String question);
    
    @Modifying
    @Query(value = "update quiz u set u.OPTION_1 = :OPTION_1 where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateOption_1(@Param(value = "ID") Long quiz_id, @Param(value = "OPTION_1") String option_1);

    @Modifying
    @Query(value = "update quiz u set u.OPTION_2 = :OPTION_2 where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateOption_2(@Param(value = "ID") Long quiz_id, @Param(value = "OPTION_2") String option_2);

    @Modifying
    @Query(value = "update quiz u set u.OPTION_3 = :OPTION_3 where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateOption_3(@Param(value = "ID") Long quiz_id, @Param(value = "OPTION_3") String option_3);

    @Modifying
    @Query(value = "update quiz u set u.CORRECT_OPTION = :CORRECT_OPTION where u.ID = :ID", nativeQuery = true)    
    @Transactional
    void updateCorrectOption(@Param(value = "ID") Long quiz_id, @Param(value = "CORRECT_OPTION") Integer correctOption);


}
