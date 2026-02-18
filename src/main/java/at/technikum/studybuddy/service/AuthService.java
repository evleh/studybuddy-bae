package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.Token;
import at.technikum.studybuddy.dto.TokenRequest;
import at.technikum.studybuddy.security.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Token createToken(TokenRequest tokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenRequest.getUsername(),
                        tokenRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // real JWT here
        String jwt = JWT.create()
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.DAYS))
                .withClaim("userId", userPrincipal.getId())
                .withClaim("userName", userPrincipal.getUsername())
                .withIssuer("studybuddy-bae")
                .withIssuedAt(Date.from(Instant.now()))
                .sign(Algorithm.HMAC256("le secret"))
                ;


        Token token = new Token();
        token.setAccessToken(jwt);
        token.setUserId(Long.toString(userPrincipal.getId()));


        return token;
    }


}