package com.example.Moviez.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${spring.security.oauth2.client.registration.google.client-id}")
        String clientId;

        @Value("${spring.security.oauth2.client.registration.google.client-secret}")
        String clientSecret;
        @Bean
        public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception
        {
                http
                        .authorizeHttpRequests((authorize) -> authorize
                                // Make the home page public
                                .requestMatchers("/", "/index.html").permitAll()
                                // Any other request (like /profile) REQUIRES a login
                                .anyRequest().authenticated()
                        )
                        // Enable the Google login page
                        .oauth2Login(Customizer.withDefaults());

                return http.build();

        }

        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
                return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
        }

        private ClientRegistration googleClientRegistration() {
                return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId(clientId)

                        .clientSecret(clientSecret)
                        .build();
        }
}
