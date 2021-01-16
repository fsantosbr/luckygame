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
    private String cpf;
    private String email;
    private String password;
    private Boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "luckyGame_id")
    private LuckyGame luckyGame; // player has just one game

    @OneToMany(mappedBy = "player")
    private List<MyLuckyNumber> myLuckNumbers = new ArrayList<>(); // player has many numbers


    // Constructors
    public Player(){}

    public Player(Long id, String name, String cpf, String email, String password, Boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }   

    public Player(Long id, String name, String cpf, String email, String password,  Boolean isAdmin, LuckyGame luckyGame) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public List<MyLuckyNumber> getMyLuckNumbers() {
        return myLuckNumbers;
    }   

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
        

    // Methods of this class

   

    // Overrided methods


    @Override
    public String toString() {
       return "name: " + name + " cpf: " + cpf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        return true;
    }
  
}