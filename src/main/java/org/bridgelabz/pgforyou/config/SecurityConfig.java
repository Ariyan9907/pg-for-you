package org.bridgelabz.pgforyou.config;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;


    // Password encoder
    // Used during registration to encrypt the password
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> web.ignoring()
                .requestMatchers("/WEB-INF/views/**");
    }


    // Authentication Provider
    // Responsible for authenticating user using UserDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    // Authentication Manager
    // Used by AuthService during login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    // Handles unauthenticated users
    // 401 Unauthorized
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {

        return (request, response, authException) -> {

            response.setStatus(401);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Unauthorized - Please login\"}"
            );
        };
    }


    // Handles authenticated users
    // who don't have sufficient permission
    // 403 Forbidden
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, accessDeniedException) -> {

            response.setStatus(403);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Access denied - Insufficient permissions\"}"
            );
        };
    }


    // Main Spring Security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // Disable CSRF
                // JWT authentication is stateless
                .csrf(csrf -> csrf.disable())


                // Don't create HTTP sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // Exception handling
                .exceptionHandling(exception -> exception

                        // User is not authenticated
                        .authenticationEntryPoint(
                                authenticationEntryPoint()
                        )

                        // User is authenticated but
                        // doesn't have required role
                        .accessDeniedHandler(
                                accessDeniedHandler()
                        )
                )


                // Register our authentication provider
                .authenticationProvider(
                        authenticationProvider()
                )


                // Authorization rules
                .authorizeHttpRequests(auth -> auth


                        // =================================
                        // PUBLIC ENDPOINTS
                        // =================================

                        // Home page
                        .requestMatchers("/")
                        .permitAll()

                        // Login and register JSP pages
                        .requestMatchers(
                                "/login",
                                "/register"
                        )
                        .permitAll()

                        // Login and register form submissions
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register"
                        )
                        .permitAll()

                        // Static resources
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/js/**",
                                "/favicon.ico"
                        )
                        .permitAll()


                        // =================================
                        // ADMIN ENDPOINTS
                        // =================================

                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")


                        // =================================
                        // USER + ADMIN ENDPOINTS
                        // =================================

                        .requestMatchers(
                                "/api/pgs/**",
                                "/api/rooms/**",
                                "/api/bookings/**",
                                "/api/reviews/**"
                        )
                        .hasAnyRole("USER", "ADMIN")


                        // =================================
                        // EVERYTHING ELSE
                        // =================================

                        .anyRequest()
                        .authenticated()
                )


                // =================================
                // JWT FILTER
                // =================================

                // Run JWT authentication before
                // Spring's username/password filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}