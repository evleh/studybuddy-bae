/**
 * Taken from eww-bae-25 quite verbatim initionally
 */

package at.technikum.studybuddy.security;

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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("Check Token here!");
        System.out.println(request.getHeader("Authorization"));

        // check JWT, if valid read user data and create authentication

        if (null == request.getHeader("Authorization")) {
            filterChain.doFilter(request, response);
            return;
        }

        this.verifyToken(request
                .getHeader("Authorization")
                .replace("Bearer ", "")
        );


        filterChain.doFilter(request, response);
    }
    private void verifyToken(String jwt) {
        String userId = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("le secret"))
                    // specify any specific claim validations
                    .withIssuer("studybuddy-bae")
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);
            userId = decodedJWT.getClaim("userId").asString();

        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new RuntimeException(exception.getMessage());
        }

        /*String userId = request
                .getHeader("Authorization")
                .replace("Bearer ", "");*/

        Authentication authentication = new UserPrincipalAuthenticationToken(
                new UserPrincipal(
                        userId,
                        "testuser",
                        "",
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // hashTag findMeAgain #findmeagain also TODO
    }

}