package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class LuckyGame implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    
    @Size(min = 5, max = 30, message  = "O nome precisa ter entre 5 e 30 caracteres")
    private String title;

    @NotNull(message = "Precisa informar a quantidade de ganhadores permitidos")
    private Integer numWinners;
   
    private Instant momentCreated;
    private Boolean isClosed;
    private Boolean alive; // if the value is false, the game can't be edit or lottery again - Change this value when run the sort
    
    @Size(min = 5, max = 100, message = "Comunicado precisa ser entre 5 e 100 caracteres")
    private String communicateAll; // to pass information to all the players

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Player owner; //game has one ower in player
    
    // insert jsonIgnore here when we'll work with API
    @ManyToMany(mappedBy = "luckyGames")
    private List<Player> players = new ArrayList<>(); //luckygame has many players

    // insert jsonIgnore here when we'll work with API
    @OneToMany(mappedBy = "game")
    private List<MyLuckyNumber> playerLuckyNumbers = new ArrayList<>(); // game has many numbers

    public LuckyGame() {        
    }  
    
    public LuckyGame(Long id, String title, Integer numWinners, Instant momentCreated, Boolean isClosed, Boolean alive, String communicateAll, Player owner) {
        this.id = id;
        this.title = title;
        this.numWinners = numWinners;
        this.momentCreated = momentCreated;
        this.isClosed = isClosed;
        this.alive = alive;
        this.communicateAll = communicateAll;
        this.owner = owner;
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

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<MyLuckyNumber> getPlayerLuckyNumbers() {
        return playerLuckyNumbers;
    }


    // Methods of this class   

    /*
        OBS: if we create a method to add players to the list, It won't work on database.
        For this case, we must use queries to update the database.
    */
 

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
