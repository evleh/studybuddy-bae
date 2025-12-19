package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Token;
import at.technikum.studybuddy.dto.TokenRequest;
import at.technikum.studybuddy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public Token token(@RequestBody @Valid TokenRequest tokenRequest) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        boolean x = "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().toString());
        System.err.format("%s\n", SecurityContextHolder.getContext().getAuthentication().toString());
        System.err.format("%s\n", SecurityContextHolder.getContext().getAuthentication().getClass());
        System.err.println(org.springframework.security.authentication.AnonymousAuthenticationToken.class);
        return authService.createToken(tokenRequest);
    }
}