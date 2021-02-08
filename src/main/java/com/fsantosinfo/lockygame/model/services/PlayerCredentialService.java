package com.fsantosinfo.lockygame.model.services;

import java.util.Optional;

import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
This service will handle the operations to encrypt and compare passwords when..
saving a new player or checking the credentials.
*/

@Service
public class PlayerCredentialService implements UserDetailsService {   

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // here - we use a dto or a repository to bring the data

        Player player = playerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = player.getIsAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
        // This line will decide if a user/player is admin or not.
        // Those roles are not in use yet.

        return User
                .builder()
                .username(player.getEmail())
                .password(player.getPassword())
                .roles(roles)
                .build();
    }

    @Transactional
    public Player save(Player player){
        return playerRepository.save(player);
    }

    public Player getLoggedPlayer() {
        Player player;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails){
			username = ((UserDetails) principal).getUsername();

            Optional<Player> playerOptional = playerRepository.findByEmail(username);
            player = playerOptional.get();
		}
		else{
			username = principal.toString();
            player = new Player();
            player.setName(username);
		}
        
        return player;
    }
    
    public String getLoggedEmailOwner() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails){
			username = ((UserDetails) principal).getUsername();
		}
		else{
			username = principal.toString();
		}
        return username;
	}

	public Boolean checkUsernameAndCPF(String email, String cpf) {
		Boolean finalResult = false;

        Optional<Player> playerByEmail = playerRepository.findByEmail(email);
        Optional<Player> playerByCPF = playerRepository.findByCpf(cpf);
        // NOTE: Implement the unique field in the DB. Without it, the field will have duplicate values and this querie will break
        
        if (!playerByEmail.isPresent()){
            finalResult = true;
            if(!playerByCPF.isPresent()){
                finalResult = true;
            }
        }

        return finalResult;
	}
}
