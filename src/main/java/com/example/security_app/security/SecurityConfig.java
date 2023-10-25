package com.example.security_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
             
         User.withUsername("nasser").password(encoder.encode("1234")).roles("ADMIN",
        "USER").build(),
        User.withUsername("saad").password(encoder.encode("0000")).roles("USER").build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(req -> req
                        .defaultSuccessUrl("/home")
                        // Redirect to "/success" if information are correct
                        .permitAll())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/home")
                        .permitAll()
                        .requestMatchers("/admin/**", "/user/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/user/**")
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated());

        return httpSecurity.build();
    }
}