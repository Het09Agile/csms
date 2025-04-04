package com.csms.config;

import com.csms.repository.UserRepository;
import com.csms.utils.enums.Role;
import com.csms.utils.exception.CustomAccessDeniedHandler;
import com.csms.utils.exception.CustomAuthenticationEntryPoint;
import com.csms.utils.filter.JwtTokenValidatorFilter;
import com.csms.utils.jwt.JwtUtils;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@AllArgsConstructor
@EnableJpaAuditing
public class ApplicationConfig {
    private final Environment environment;
    private final UserRepository userRepository;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain pathSecurity(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilterBefore(new JwtTokenValidatorFilter(environment,userRepository), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/signup","/auth/login").permitAll()
//                        .requestMatchers("/api/onlyBatsman").hasAuthority("BATSMAN")
                        .requestMatchers("/api/**").authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(hbc->hbc.disable());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder){
        AuthenticationProviderImpl authenticationProvider = new AuthenticationProviderImpl(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public JwtUtils jwtUtil() {
        final SecretKey secretKey = Keys.hmacShaKeyFor(environment.getProperty("jwt.secret").getBytes());
        return new JwtUtils(secretKey);
    }
}
