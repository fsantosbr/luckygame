package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "luckyGame_id")
    private LuckyGame luckyGame; // player has just one game

    @OneToMany(mappedBy = "player")
    private List<SortNumber> sortNumbers = new ArrayList<>(); // player has many numbers


    // Constructors
    public Player(){}

    public Player(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Player(Long id, String name, String email, String password, LuckyGame luckyGame) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.luckyGame = luckyGame;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LuckyGame getLuckyGame() {
        return luckyGame;
    }

    public void setLuckyGame(LuckyGame luckyGame) {
        this.luckyGame = luckyGame;
    }

    public List<SortNumber> getSortNumbers() {
        return sortNumbers;
    }
    

    // Methods of this class

   

    // Overrided methods


    @Override
    public String toString() {
       return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        Player other = (Player) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
  
}