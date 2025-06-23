package com.archpatterns.walletmanager.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.archpatterns.walletmanager.services.JwtAuthorizationFilter;
import com.archpatterns.walletmanager.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.archpatterns.walletmanager.enums.TipoRole.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {
	private static final String ROLE_PREFIX = "ROLE_";

	private static final String[] WHITELIST = new String[] {
			"/manage/health", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**" };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService,
			AuthenticationConfiguration config) throws Exception {

		http.csrf().disable()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(GET, WHITELIST).permitAll()
						.requestMatchers(POST, "/api/wallet/create")
						.hasAnyAuthority(ROLE_PREFIX + ADMIN.name(), ROLE_PREFIX + PUBLIC.name())
						.requestMatchers(GET, "/api/wallet/{user_id}/balance")
						.hasAnyAuthority(ROLE_PREFIX + ADMIN.name(), ROLE_PREFIX + OWNER.name())
						.requestMatchers(POST, "/api/wallet/{user_id}/deposit")
						.hasAnyAuthority(ROLE_PREFIX + ADMIN.name(), ROLE_PREFIX + OWNER.name())
						.requestMatchers(POST, "/api/wallet/{user_id}/withdraw")
						.hasAnyAuthority(ROLE_PREFIX + ADMIN.name(), ROLE_PREFIX + OWNER.name())
						.requestMatchers(GET, "/api/wallet/{user_id}/history")
						.hasAnyAuthority(ROLE_PREFIX + ADMIN.name(), ROLE_PREFIX + OWNER.name())
						.anyRequest().authenticated())
				.addFilterAfter(new JwtAuthorizationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(STATELESS);

		return http.build();
	}

	@Bean
	public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationConfiguration config)
			throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager(config));
		return filter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
