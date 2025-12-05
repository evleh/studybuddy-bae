package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUserAdminIfNecessary() {
        //arrange
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        //act
        userService.createUserAdminIfNecessary();

        //assert
        Mockito.verify(userRepository).findByUsername(Mockito.anyString());
    }


    @Test
    void testCreateUserAdminIfNecessaryUserExists() {
        // arrange
        User user = new User();
        user.setUsername("admin");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));

        // act
        userService.createUserAdminIfNecessary();

        // assert
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

}