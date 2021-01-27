package com.fsantosinfo.lockygame.model.config;

import com.fsantosinfo.lockygame.model.services.PlayerCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Class with all configurations from Spring Security

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PlayerCredentialService playerCredentialService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // here is defined where the users come from
        // inMemoryAuthentication() - in memory
        // userDetailsService() - Find and load an user from database

        auth.userDetailsService(playerCredentialService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Here we define the authorizations
        // .csrf() - used for the backend - enable when we start working with another database instead of H2

        http
                .csrf().disable()
                .authorizeRequests()

                    .antMatchers("/lucky-game/view/**")
                        .authenticated()
                    .antMatchers("/lucky-game/edit/**")
                        .authenticated()
                    .antMatchers("/lucky-game/enter/**")
                        .authenticated()
                    .antMatchers("/dashboard/player/**")
                        .authenticated()
                    .antMatchers("/v/lucky-game/**")
                        .authenticated()
                    .antMatchers("/new-lucky-game")
                        .authenticated()

                    .antMatchers("/h2-console/**")
                        .permitAll()
                    .antMatchers("/signup")
                        .permitAll()                    
                    .antMatchers("/lucky-game/invite/**")
                        .permitAll()
                    .antMatchers("/players")
                        .permitAll()
                    .antMatchers("/games")
                        .permitAll()
                    .antMatchers("/")
                        .permitAll()

                .and()
                    .formLogin();

        http.headers().frameOptions().disable();
        //This line and the csrf() disabled are necessary to the h2 database can work properly.

    }

}
