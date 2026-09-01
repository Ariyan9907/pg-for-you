package org.bridgelabz.pgforyou.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.LoginRequestDTO;
import org.bridgelabz.pgforyou.dto.request.RegisterRequestDTO;
import org.bridgelabz.pgforyou.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Show register page
    @GetMapping("/register")
    public String showRegisterPage() {

        System.out.println("REGISTER CONTROLLER CALLED");

        return "register";
    }

    // Register user from JSP form
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute RegisterRequestDTO requestDTO,
            Model model) {

        authService.register(requestDTO);

        return "redirect:/login";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginRequestDTO requestDTO,
            HttpServletResponse response,
            Model model) {

        try {

            String token = authService.login(requestDTO);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false) // true when using HTTPS
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Lax")
                    .build();

            response.addHeader(
                    HttpHeaders.SET_COOKIE,
                    cookie.toString()
            );

            return "redirect:/";

        } catch (AuthenticationException e) {

            model.addAttribute(
                    "error",
                    "Invalid email or password"
            );

            return "login";
        }
    }
}