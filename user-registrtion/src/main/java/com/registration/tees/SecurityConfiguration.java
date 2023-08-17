package com.registration.tees;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.registration.security.jwt.AuthTokenFilter;

@Configuration
public class SecurityConfiguration {

	@Bean
	public FilterRegistrationBean<AuthTokenFilter> securityFilterRegistration() {
		FilterRegistrationBean<AuthTokenFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new AuthTokenFilter());
		registration.addUrlPatterns("/*");
		registration.setName("securityFilter");
		registration.setOrder(1);
		return registration;
	}
}
