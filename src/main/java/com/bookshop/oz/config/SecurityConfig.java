package com.bookshop.oz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bookshop.oz.service.PersonDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final PersonDetailsService persService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(persService).passwordEncoder(getPasswordEncoder());
	}
	

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/* CHECK PersonDetails in case nothing will work */
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests((requests) -> requests.
//				requestMatchers("/personal").hasAnyRole("ADMIN", "SHOP_ASSISTANT", "CUSTOMER", "ECONOMIST").
//				requestMatchers("/catalog", "/catalog/{isbn}", "/personal/login", "/css/..").permitAll().anyRequest().authenticated())
//				.formLogin((form) -> form.loginPage("/personal/login").defaultSuccessUrl("/catalog", true)
//						.failureUrl("/personal/login?error").permitAll())
//				.logout((logout) -> logout.permitAll());
//		return http.build();
		http.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/catalog", "/catalog/{isbn}", "/personal/login", "/css/..").permitAll().anyRequest().authenticated());
		return http.build();
	}
}
