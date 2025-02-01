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

//commit-is-1
//commit-is-2
//commit-is-3

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
