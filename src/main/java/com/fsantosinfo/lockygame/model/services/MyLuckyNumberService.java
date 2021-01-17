package com.fsantosinfo.lockygame.model.services;

import java.util.List;
import java.util.Optional;

import com.fsantosinfo.lockygame.model.entities.MyLuckyNumber;
import com.fsantosinfo.lockygame.repositories.MyLuckyNumberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class will help with some the business logic

@Service
public class MyLuckyNumberService {

    @Autowired
    private MyLuckyNumberRepository repository;

    public List<MyLuckyNumber> findAll() {
        return repository.findAll();
    }

    public void save(MyLuckyNumber myLuckyNumber) {        
        repository.save(myLuckyNumber);
    }

    public MyLuckyNumber findById(Long id) {
        Optional<MyLuckyNumber> optionalNumber = repository.findById(id);
        return optionalNumber.get();
    }
    
}
