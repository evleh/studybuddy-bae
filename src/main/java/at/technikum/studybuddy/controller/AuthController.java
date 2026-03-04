package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Token;
import at.technikum.studybuddy.dto.TokenRequest;
import at.technikum.studybuddy.service.AuthService;
import at.technikum.studybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService,UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public Token token(@RequestBody @Valid TokenRequest tokenRequest) {
        Token token = authService.createToken(tokenRequest);
        this.userService.updateLastLogin(tokenRequest.getUsername());
        return token;
    }
}