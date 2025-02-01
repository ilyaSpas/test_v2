package ru.my.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppSecurityConfiguration.class)
public class SecurityStarterAutoConfiguration {
}
