package com.abj.EmpMgmtSys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Allow frontend origin
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() {
		return new ProviderManager(Arrays.asList(authenticationProvider()));
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		HttpStatusReturningLogoutSuccessHandler logoutSuccessHandler = new HttpStatusReturningLogoutSuccessHandler();

		http
				.authenticationProvider(authenticationProvider())
				.cors().configurationSource(corsConfigurationSource()).and()
				.csrf().disable()
				.headers().frameOptions().disable()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/login", "/loginPage*").permitAll() // Allow unauthenticated access to /login and
																	// /loginPage endpoints
				.antMatchers("/authenticate/**").permitAll() // Only allow unauthenticated access to the login endpoint
				.antMatchers("/employees/list", "/employees/form").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN") // Only
																												// allow
																												// users
																												// with
				// USER or ADMIN roles
				// to access these
				// endpoints
				.antMatchers("/employees/list").permitAll()
				.antMatchers(HttpMethod.POST, "/employees/save").permitAll()
				// access this endpoint
				.antMatchers("/employees/update").permitAll()
				// access this endpoint
				.antMatchers("/employees/delete").permitAll() // Allow all requests to /employees/delete
				.antMatchers("/logout").authenticated() // Ensure authenticated access to /logout
				.anyRequest().authenticated() // Require authentication for all other endpoints
				.and()
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
						.logoutSuccessHandler(logoutSuccessHandler));
		// .httpBasic();
		return http.build();
	}
}