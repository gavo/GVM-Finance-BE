package com.bo.gvm_finance_be.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        .sessionManagement(session -> session
            .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS));
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    String frontendUrl = System.getProperty("app.frontend-url", "http://localhost:3000");
    String allowedMethods = System.getProperty("app.cors.allowed-methods", "GET,POST,PUT,DELETE,OPTIONS");
    String allowedHeaders = System.getProperty("app.cors.allowed-headers", "Content-Type,Authorization");

    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();

    config.setAllowedOrigins(Arrays.asList(frontendUrl.split(",")));
    config.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
    config.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
