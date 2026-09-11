package com.example.Moviez.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(authz -> authz
                                // 1. Allow PayU webhooks without a token
                                .requestMatchers("/api/payu/notify").permitAll()
                                .requestMatchers("/api/showings/**").permitAll()
                                .requestMatchers("/api/movies/**").permitAll()
                                // 2. Keep all other endpoints locked down
                                .anyRequest().authenticated()
                        )
                        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                // Allow the React app's exact URL
                configuration.setAllowedOrigins(List.of("http://localhost:5173"));

                // Allow standard HTTP methods, including OPTIONS which is required for preflight
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

                // Allow the Keycloak Authorization header
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration); // Apply to all API routes
                return source;
        }
}