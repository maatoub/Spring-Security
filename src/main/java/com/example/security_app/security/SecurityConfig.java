
package com.example.security_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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
        // httpSecurity.formLogin(login -> login.loginPage("/login").permitAll());
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(req -> req.loginPage("/login")
                        .defaultSuccessUrl("/home")
                        // Redirect to "/home" if information are correct
                        .permitAll())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/home")
                        .permitAll()
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/auth/**")
                        .hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .authenticated());
        httpSecurity.exceptionHandling(
                handling -> handling.accessDeniedPage("/notAuthorized"))
                .rememberMe(remember -> remember.key("myKey").tokenValiditySeconds(8000));

        return httpSecurity.build();
    }
}
