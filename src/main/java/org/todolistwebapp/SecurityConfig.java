package org.todolistwebapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Die Konfiguration von Spring Security
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // erlaubte Requests ohne Login
                .antMatchers("/helloworld**").permitAll()
                .antMatchers("/login**","/callback/", "/webjars/**", "/error**").permitAll()
                // Der Rest muss authentifiziert sein
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

}