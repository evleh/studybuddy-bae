/**
 * Taken from eww-bae-25 quite verbatim initionally
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

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
        String role = null;
        String userName = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("le secret"))
                    // specify any specific claim validations
                    .withIssuer("studybuddy-bae")
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);
            userId = decodedJWT.getClaim("userId").asString();
            role = decodedJWT.getClaim("role").asString();
            userName = decodedJWT.getClaim("userName").asString();


        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new RuntimeException(exception.getMessage());
        }


        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("userName"));

        UserPrincipal myUserPrincipal =  new UserPrincipal(
                Long.toString(user.getId()),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );

        Authentication authentication = new UserPrincipalAuthenticationToken(myUserPrincipal);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        // hashTag findMeAgain #findmeagain also TODO
    }

}