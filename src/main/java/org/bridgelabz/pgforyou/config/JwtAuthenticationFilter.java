package org.bridgelabz.pgforyou.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.service.JwtService;
import org.bridgelabz.pgforyou.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;


    // Skip JWT authentication for public resources
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/register")
                || path.equals("/login")
                || path.equals("/auth/register")
                || path.equals("/auth/login")
                || path.startsWith("/WEB-INF/views/")
                || path.startsWith("/css/")
                || path.startsWith("/images/")
                || path.startsWith("/js/")
                ||path.equals("/favicon.ico");
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println(
                "JWT FILTER: "
                        + request.getRequestURI()
                        + " | "
                        + request.getDispatcherType()
        );

        // Get JWT from browser cookie
        String token = getJwtFromCookie(request);

        // No JWT → continue request
        if (token == null) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            // Extract email from JWT
            String email = jwtService.extractEmail(token);

            // Check user is not already authenticated
            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                // Find user from database
                UserDetails userDetails =
                        userService.loadUserByUsername(email);

                // Check JWT expiration
                if (!jwtService.isTokenExpired(token)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Add request details
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // Store authentication
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {

            // Invalid JWT
            SecurityContextHolder.clearContext();
        }

        // Continue request
        filterChain.doFilter(request, response);
    }


    // Get JWT from browser cookie
    private String getJwtFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}