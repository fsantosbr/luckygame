package com.fsantosinfo.lockygame.model.services;

import com.fsantosinfo.lockygame.model.entities.Player;
import com.fsantosinfo.lockygame.model.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
This service will handle the operations to encrypt and compare passwords when..
saving a new player or checking the credentials.
*/

@Service
public class PlayerCredentialService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

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
}
