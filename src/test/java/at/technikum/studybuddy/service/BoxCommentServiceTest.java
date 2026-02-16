package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class BoxCommentServiceTest {

    @Mock
    private BoxCommentRepository boxCommentRepository;

    @Mock
    private BoxRepository boxRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BoxCommentService boxCommentService;

    private UserPrincipal adminPrincipal = TestPrincipalFactory.admin();

    private UserPrincipal registeredPrincipal = TestPrincipalFactory.registeredUser(42L);;


    @Test
    void readAllOnEmptyRepository(){
        Mockito.when(boxCommentRepository.findAll()).thenReturn(Collections.emptyList());
        List<BoxComment> comments = boxCommentService.readAll();
        assertTrue(comments.isEmpty());
    }

    @Test
    void readRessourceNotFoundAsAdmin(){ // ressource does not exist
        Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> boxCommentService.read(1L, adminPrincipal));
    }

    @Test
    void shouldThrowAccessDeniedWhenUserReadsForeignComment(){ // permission to read own ressource

    }

    // user can't read ressource of someone else

    // admin can read everything





}
