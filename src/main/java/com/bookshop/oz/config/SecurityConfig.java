package com.bookshop.oz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/catalog", "/catalog/{isbn}").permitAll())
				.formLogin((form) -> form.loginPage("/auth/login").defaultSuccessUrl("/catalog", true)
						.failureUrl("/auth/login?error").permitAll())
				.logout((logout) -> logout.permitAll());
		return http.build();
	}
}
