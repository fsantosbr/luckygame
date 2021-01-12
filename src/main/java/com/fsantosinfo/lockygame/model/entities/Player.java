package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private String password;

    private LuckyNumber luckyNumber;

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

    public LuckyNumber getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(LuckyNumber luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    // Methods of this class

   

    // Overrided methods


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", luckyNumber=" + luckyNumber +
                '}';
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