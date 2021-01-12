package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class LuckyGame implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotBlank
    private String title;

    private Integer numWinners;
   // here was a list of players - players
   // here was a list of players - winners   

    private Instant momentCreated;    
    private Boolean open;
    private Boolean alive; // if the value is false, the game can't be edit or lottery again - Change this value when run the sort
    private String communicateAll; // to pass information when the game is not alive or others reasons

    @NotBlank
    private String emailOwner;
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "luckyGame")
    private List<Player> players;

    public LuckyGame() {        
    }  
    
    public LuckyGame(Long id, String title, Integer numWinners, Instant momentCreated, Boolean open, Boolean alive, String communicateAll, String emailOwner, String password) {
        this.id = id;
        this.title = title;
        this.numWinners = numWinners;
        this.momentCreated = momentCreated;
        this.open = open;
        this.alive = alive;
        this.communicateAll = communicateAll;
        this.emailOwner = emailOwner;
        this.password = password;
}
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumWinners() {
        return numWinners;
    }

    public void setNumWinners(Integer numWinners) {
        this.numWinners = numWinners;
    }   

    public Instant getMomentCreated() {
        return momentCreated;
    }

    public void setMomentCreated(Instant momentCreated) {
        this.momentCreated = momentCreated;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public String getCommunicateAll() {
        return communicateAll;
    }

    public void setCommunicateAll(String communicateAll) {
        this.communicateAll = communicateAll;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    // Methods of this class   


    // Overrided methods


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
        LuckyGame other = (LuckyGame) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
   
}
