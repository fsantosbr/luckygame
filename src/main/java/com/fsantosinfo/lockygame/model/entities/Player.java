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
    
    @Size(min = 5, max = 30, message  = "O nome precisa ter entre 5 e 30 caracteres")
    private String name;
    
    @NotNull(message = "O campo CPF não pode estar em branco.")
    private String cpf;

    @Email(message = "O email precisa ser válido")
    private String email;

    @NotBlank
    private String password;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "owner")
    private List<LuckyGame> ownerGames = new ArrayList<>(); // player has many own games

    @ManyToMany
    @JoinTable(name = "tb_player_lucky_game", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "luckygame_id"))
    private List<LuckyGame> luckyGames = new ArrayList<>(); // player has many games

    // insert jsonIgnore here when we'll work with API
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
        

    // Methods of this class

    /*
    OBS: If we create a method to add number to the list, It won't work on data base.
    The numbers must persisted with the player in the constructor.
    */

    // Overrided methods


    @Override
    public String toString() {
       return name + " - " + email;
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