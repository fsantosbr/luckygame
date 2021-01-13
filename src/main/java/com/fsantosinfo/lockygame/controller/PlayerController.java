package com.fsantosinfo.lockygame.controller;

import com.fsantosinfo.lockygame.model.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping("/players")
    public ModelAndView players(){

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("players");
        
        modelAndView.addObject("allPlayers", service.findAll());
        return modelAndView;
        // note: This path is only for test. It won't exist on the production fase
    }

    
}
