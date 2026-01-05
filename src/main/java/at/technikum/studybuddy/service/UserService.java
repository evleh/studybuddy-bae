package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.dto.UserDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.EntityAlreadyExistsException;
import at.technikum.studybuddy.exceptions.EntityNotFoundException;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.security.UserPrincipal;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> readAll(){
        return this.userRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_REGISTERED') && #id == authentication.principal.id)")
    public UserDto read(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return new UserDtoPrivilegedInfo(userOptional.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_REGISTERED') && #id == authentication.principal.id)")
    public User update(long id, User user) {
        Optional<User> findUser = userRepository.findById(id); // save info if user already exists
        if(findUser.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.userRepository.save(user);
        return findUser.get();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User delete(long id){
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        this.userRepository.deleteById(id);
        return user.get();
    }

    public void createUserAdminIfNecessary() {
        if (userRepository.findByUsername("admin").isPresent()) {
            return;
        }
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setAdmin(true);
        admin.setEmail("admin@example.com");
        admin.setGender("admin");
        admin.setCountry("AT");
        admin.setLastname("AD");
        admin.setFirstname("MIN");
        userRepository.save(admin);


        // normal user
        User normal = new User();
        normal.setUsername("normal");
        normal.setPassword(passwordEncoder.encode("normal"));
        normal.setAdmin(false);
        normal.setEmail("admin@example.com");
        normal.setGender("user");
        normal.setCountry("AT");
        normal.setLastname("US");
        normal.setFirstname("ER");
        userRepository.save(normal);
    }
    public User register(Registration registration) {
        userRepository.findByUsername(registration.getUsername())
                .ifPresent(user -> {throw new EntityAlreadyExistsException();});

        User user = new User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setEmail(registration.getEmail());
        user.setGender(registration.getGender());
        user.setLastname(registration.getLastname());
        user.setFirstname(registration.getFirstname());
        user.setCountry(registration.getCountry());

        return userRepository.save(user);
    }
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }

    public boolean isCurrentUserRegistered(){
        return org.springframework.security.authentication.AnonymousAuthenticationToken.class !=
                        SecurityContextHolder.getContext().getAuthentication().getClass();

    }

    public boolean isCurrentUserAdmin() {
        try {
            String userName = this.getUserNameOfCurrentUser();
            return this.getByUsername(userName).isAdmin();
        } catch(RuntimeException e) {
            System.err.format("%s", e.toString());
            return false;
        }
    }

    public String getUserNameOfCurrentUser() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch(RuntimeException e) {
            System.err.format("%s", e.toString());
            return "not logged in";
        }

    }

    public boolean isOwner(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPricipal = (UserPrincipal) auth.getPrincipal();
        Long userId = userPricipal.getId();
        return userId.equals(id);
    }

    public boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

}
