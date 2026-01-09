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
        // Uncomment next 2 lines to test workings of the userService.isCurrentUserRegistered() method
        // System.out.print("registered? -> ");
        // System.out.println(userService.isCurrentUserRegistered());
        return authService.createToken(tokenRequest);
    }
}