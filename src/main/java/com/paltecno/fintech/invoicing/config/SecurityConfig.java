package com.paltecno.fintech.invoicing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // السماح بدخول الكونسول للجميع
                        .anyRequest().authenticated() // باقي الصفحات تتطلب تسجيل دخول
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // تعطيل حماية CSRF الخاصة بالكونسول
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // السماح بعرض الكونسول

        return http.build();
    }
}
