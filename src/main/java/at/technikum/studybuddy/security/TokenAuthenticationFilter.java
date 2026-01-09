/**
 * Taken from eww-bae-25 quite verbatim initially
 */

package at.technikum.studybuddy.security;

import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public TokenAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("Check Token here!");
        System.out.println(request.getHeader("Authorization"));

        // 1. decide whether we want to apply the filter? Only if request has the right header, we want to proceed with
        // authentication. Otherwise, we apply the filter chain with an early return.
        if (null == request.getHeader("Authorization")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Check credentials (JWT) and [authenticate | reject ]. If authenticated, authentication object is created
        this.verifyToken(request
                .getHeader("Authorization")
                .replace("Bearer ", "")
        );

        // 3. call next filter
        filterChain.doFilter(request, response);
    }
    private void verifyToken(String jwt) {
        String userId = null;
        String userName = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("le secret"))
                    // specify any specific claim validations
                    .withIssuer("studybuddy-bae")
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);
            userId = decodedJWT.getClaim("userId").asString();
            userName = decodedJWT.getClaim("userName").asString();


        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new RuntimeException(exception.getMessage());
        }


        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("userName"));

        UserPrincipal myUserPrincipal = new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );

        Authentication authentication = new UserPrincipalAuthenticationToken(myUserPrincipal);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.printf("[%s] logged in with authorities [%s]%n", authentication.getName(), authentication.getAuthorities() );

        // hashTag findMeAgain #findmeagain also TODO
    }

}