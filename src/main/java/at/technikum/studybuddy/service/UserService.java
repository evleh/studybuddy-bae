package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.EntityAlreadyExistsException;
import at.technikum.studybuddy.exceptions.EntityNotFoundException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipalAuthenticationToken;
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

    public List<User> readAll(){
        return this.userRepository.findAll();
    }

    public User read(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user.get();
    }

    public User update(long id, User user) {
        Optional<User> findUser = userRepository.findById(id); // save info if user already exists
        if(findUser.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.userRepository.save(user);
        return findUser.get();
    }

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

    /**
     * Altin advises to this switch form to distinguish by class-of-return value;
     * It casts and makes results usable-as-their-type.
     * Not necessary here, but instructive?
     */
    public boolean isCurrentUserRegistered(){
        switch (SecurityContextHolder.getContext().getAuthentication()) {
            case UserPrincipalAuthenticationToken studybuddyToken:
                return true;
            case org.springframework.security.authentication.AnonymousAuthenticationToken anonymousAuthenticationToken:
                return false;
            default: // this config would treat newly added Auth classes as non-registered until changed.
                return false;
        }
        /* replaces: return org.springframework.security.authentication.AnonymousAuthenticationToken.class !=
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().isInstance();*/

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

}
