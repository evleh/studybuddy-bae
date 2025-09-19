package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Token;
import at.technikum.studybuddy.dto.TokenRequest;
import at.technikum.studybuddy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public Token token(@RequestBody @Valid TokenRequest tokenRequest) {
        return authService.createToken(tokenRequest);
    }
}