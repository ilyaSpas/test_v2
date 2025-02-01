package ru.my.lib.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//commit-1-master
//commit-2-master
//commit-3-master

//test-1
//test-2
//test-3
//test-4
//test-5

//test-2-1
//test-2-2
//test-2-3
//test-2-4
//test-2-5

//branch-for-cherry-pick
//cherry-pick-test-1

//patch-test-1

//revert-1
//revert-2
//revert-3
//revert-4

//test for squash-1

//Checkout and Rebase

//master-commit
//master-commit-1
//master-commit-2
//my-commit-1

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/private/**").authenticated() // Защита для /private
                        .anyRequest().permitAll()                      // Остальные эндпоинты доступны всем
                )
                .formLogin(form -> form.defaultSuccessUrl("/", true)) // Настройка формы логина
                .logout(logout -> logout.logoutUrl("/logout"))       // Настройка логаута
                .csrf(csrf -> csrf.disable());                      // Отключение CSRF (опционально, если используется REST API)

        return http.build();
    }
}
