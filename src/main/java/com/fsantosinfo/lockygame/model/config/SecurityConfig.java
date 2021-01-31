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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        // .csrf() - used for the backend - enable it when we start working with another database instead of H2

        http
                .csrf().disable()
                .authorizeRequests()

                    .antMatchers("/lucky-game/view/**")
                        .authenticated()
                    .antMatchers("/lucky-game/edit/**")
                        .authenticated()
                    .antMatchers("/enter/lucky-game/**")
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
                    .formLogin().loginPage("/login") // Here we changed the logon page for one customized
                        .permitAll()
                .and()
                    .rememberMe() // The default is 2 weeks - but we can add more time
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        // This line is used for security when the csrf() is disable. That help us to delivery a GET method in a logout. The best solution is to enable csrf and use POST
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/");
                        /* That is for logout. We're using the already built-in Spring Security method to logout.
                         * By default without adding this instructions, the Spring S. redirects the "/logout" to the login page.
                         * But here we add some commands to delete cookies and clean the session. The logout (or other means) without deleleCookies don't delete the cookies.
                        */


        http.headers().frameOptions().disable();
        //This line and the csrf() disabled are necessary to the h2 database can work properly.

    }

}
