package com.fsantosinfo.lockygame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class GameBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String title;
    private Integer numWinners;
    private List<Player> players = new ArrayList<>();
    private List<Player> winners = new ArrayList<>();

    // Constructors

    public GameBase(){}

    public GameBase(String title, Integer numWinners) {
        this.title = title;
        this.numWinners = numWinners;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    public Integer getNumWinners() {
        return numWinners;
    }    

    public List<Player> getWinners() {
        return winners;
    }

    public void setNumWinners(Integer numWinners) {
        this.numWinners = numWinners;
    }
    
    
    // Methods of this class

    

   

    // Overrided methods

}