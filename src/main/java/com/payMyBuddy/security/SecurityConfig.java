package com.payMyBuddy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	 @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	    auth.setUserDetailsService(userDetailsService());
	    auth.setPasswordEncoder(passwordEncoder());
	    return auth;
	}

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/", "/registration", "/registration2", "/menu").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/home").defaultSuccessUrl("/menu")
				.failureUrl("/home?error=true").permitAll()
				// .and().formLogin().loginPage("/registration").defaultSuccessUrl("/menu").failureUrl("/home?error=true").permitAll()
				.and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/home");
	}


	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
