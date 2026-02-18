package at.technikum.studybuddy.service;

import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

class TestPrincipalFactory {

    static UserPrincipal admin() {
        return new UserPrincipal(
                1L,
                "admin",
                "",
                List.of(new SimpleGrantedAuthority(RoleTypes.ADMIN))
        );
    }

    static UserPrincipal registeredUser(Long id) {
        return new UserPrincipal(
                id,
                "user" + id,
                "",
                List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED))
        );
    }
}

