package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
   
   @Size(min = 2, max = 10, message  = "O nome precisa ter entre 2 e 10 caracteres")
   private String firstName;
   
   @Size(min = 3, max = 25, message  = "O sobrenome precisa ter entre 3 e 25 caracteres")
   private String surname;
    
    @NotNull(message = "O campo CPF não pode estar em branco.")
    private String cpf;

    @Email(message = "O email precisa ser válido")
    private String email;
    @NotBlank
    private String password;

    private Boolean isAdmin;    

    // insert jsonIgnore here when we'll work with API
    @OneToMany(mappedBy = "player")
    private List<MyLuckyNumber> myLuckNumbers = new ArrayList<>(); // player has many numbers    

    @OneToMany(mappedBy = "owner")
    private List<LuckyGame> ownerGames = new ArrayList<>(); // player has many own games

    @ManyToMany
    @JoinTable(name = "tb_player_lucky_game", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "luckygame_id"))
    private List<LuckyGame> luckyGames = new ArrayList<>(); // player has many games to play

    @OneToMany(mappedBy = "player")
    private List<Eligible> eligibleNumbers = new ArrayList<>(); // Player has many eligible numbers with different games.
   

    // Constructors
    public Player(){}

    public Player(Long id, String firstName, String surname, String cpf, String email, String password, Boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }


    // Getters and Setters    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public List<LuckyGame> getOwnerGames() {
        return ownerGames;
    }

    public List<LuckyGame> getLuckyGames() {
        return luckyGames;
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

    public List<Eligible> getEligibleNumbers() {
        return eligibleNumbers;
    }
        

    // Methods of this class

    /*
    OBS: If we create a method to add number to the list, It won't work on data base.    .
    */

    
    // Overrided methods
    
    @Override
    public String toString() {
        return firstName;
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
        Player other = (Player) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
     
}