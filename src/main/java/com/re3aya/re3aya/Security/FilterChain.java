package com.re3aya.re3aya.Security;


import com.re3aya.re3aya.Util.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class FilterChain {

    private final JWTFilter jwtFilter;

    public FilterChain(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(

                        configure -> configure
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/profile/**").hasAnyRole("DOCTOR", "ADMIN","PATIENT","ASSISTANT")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}