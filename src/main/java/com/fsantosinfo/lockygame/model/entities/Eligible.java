package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// This class will handle the quantity of numbers a player may receive in a specific game
@Entity
public class Eligible implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantNumbers;
    /* This attribute is the reason the class exist.
    This value must be unique and unchanged for the Player 'X' in the Game 'Y'.
    Considering that, a Player will get a different value in a 'Z' game.
    */
     

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player; // Eligible class has just one Player

    @ManyToOne
    @JoinColumn(name = "lucky_game_id")
    private LuckyGame game; // Eligible class has just one Game

    // Constructors
    public Eligible(){}

    public Eligible(Long id, Integer quantNumbers, Player player, LuckyGame game) {
        this.id = id;
        this.quantNumbers = quantNumbers;
        this.player = player;
        this.game = game;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantNumbers() {
        return quantNumbers;
    }

    public void setQuantNumbers(Integer quantNumbers) {
        this.quantNumbers = quantNumbers;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LuckyGame getGame() {
        return game;
    }

    public void setGame(LuckyGame game) {
        this.game = game;
    }

    // Methods of this class

    /*
    OBS: If we create a method to add number to the list, It won't work on data base.    .
    */

    
    // Overrided methods

    @Override
    public String toString() {      
        return  getQuantNumbers().toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Eligible other = (Eligible) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
