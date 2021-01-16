package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MyLuckyNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer myNumber;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    
    public MyLuckyNumber(){}

    public MyLuckyNumber(Long id, Integer myNumber) {
        this.id = id;
        this.myNumber = myNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(Integer myNumber) {
        this.myNumber = myNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((myNumber == null) ? 0 : myNumber.hashCode());
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
        MyLuckyNumber other = (MyLuckyNumber) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (myNumber == null) {
            if (other.myNumber != null)
                return false;
        } else if (!myNumber.equals(other.myNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return myNumber.toString();
    }

  

    
}
