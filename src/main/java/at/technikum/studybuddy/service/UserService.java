package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.dto.UserDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.dto.UserDtoPublicInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.EntityAlreadyExistsException;
import at.technikum.studybuddy.exceptions.EntityNotFoundException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User read(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return user;
    }


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

    public UserDto delete(Long id){
        User user = this.userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        UserDtoPublicInfo userDto= new UserDtoPublicInfo(user);
        this.userRepository.deleteById(id);
        return userDto;
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
                .ifPresent(user -> {
                    throw new EntityAlreadyExistsException();
                });

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
