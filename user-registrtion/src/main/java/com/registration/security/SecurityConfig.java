package com.registration.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.registration.advice.CustomAccessDeniedHandler;
import com.registration.security.jwt.AuthEntryPointJwt;
import com.registration.security.jwt.AuthTokenFilter;
import com.registration.serviceImpl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebSecurityCustomizer{

	@Autowired
	private CustomUserDetailsService myUserDetailsService;

	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(myUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/swagger-ui/indexhtml", "/swagger-resources/**",
//				"/v2/api-docs", "/webjars/**");
//	}

	@Bean
	DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/swagger-ui/**", "/swagger-ui/indexhtml", "/swagger-resources/**",
								"/v2/api-docs", "/webjars/**")
						.permitAll().requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.userDetailsService(myUserDetailsService).httpBasic(withDefaults())
				.exceptionHandling(exceptions -> exceptions.accessDeniedHandler(new CustomAccessDeniedHandler())
						.authenticationEntryPoint(authEntryPointJwt));

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Override
	public void customize(WebSecurity web) {
		// TODO Auto-generated method stub
		web.ignoring().requestMatchers("/swagger-ui/**", "/swagger-ui/indexhtml", "/swagger-resources/**",
				"/v2/api-docs", "/v3/api-docs", "/webjars/**");
		
	}
}