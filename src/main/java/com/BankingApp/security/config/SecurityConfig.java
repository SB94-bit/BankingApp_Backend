package com.BankingApp.security.config;

import com.BankingApp.security.UserDetailsServiceImpl;
import com.BankingApp.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.beans.Customizer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        return http
                .csrf(customizer -> customizer.disable())
                //.httpBasic(Customizer.)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/user/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/user/all").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/user/allUsersDetails").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/delete").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/searchByKeyword").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/account/new").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/account/deposit").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/account/withdraw").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/account/transfer").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/account/accountDetails").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/account/transactionList").hasRole("USER"))


                        //.requestMatchers("/api/user/**").permitAll())


                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
