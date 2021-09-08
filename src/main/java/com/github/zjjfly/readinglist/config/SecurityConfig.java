package com.github.zjjfly.readinglist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by zjjfly on 2017/7/2.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderDetailsService readerDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/readinglist/*").access("hasRole('ROLE_READER')")
            .antMatchers("/main").access("hasRole('ROLE_READER')")
            .antMatchers("/**").permitAll()
            .and()
            .formLogin().loginPage("/login")
            .successForwardUrl("/main")
            .failureUrl("/login?error=true")
            .and().csrf().disable()
            .rememberMe().key("readinglistKey").tokenValiditySeconds(60);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(readerDetailsService)
        .and().inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN","READER");
    }
}
