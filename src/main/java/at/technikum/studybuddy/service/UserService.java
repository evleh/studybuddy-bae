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
import at.technikum.studybuddy.security.UserPrincipal;
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

    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_REGISTERED') && authentication.principal.id.equals(#id))")
    public User read(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return user;
    }

    // todo only admin can change admin attribute
    // todo change password: sollte man wsl extra machen
    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_REGISTERED') && authentication.principal.id.equals(#id))")
    public User update(Long id, UserDtoPrivilegedInfo userDto) {
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new); // save info if user already exists

        user.setFoto(userDto.getFoto());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setCountry(userDto.getCountry());

        this.userRepository.save(user);
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User delete(Long id){
        User user = this.userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        this.userRepository.deleteById(id);
        return user;
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

    public String getUserNameOfCurrentUser() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch(RuntimeException e) {
            System.err.format("%s", e.toString());
            return "not logged in";
        }

    }
}
