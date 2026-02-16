package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


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

    private User user(Long id) {
        User u = new User();
        u.setId(id);
        return u;
    }

    private Box box(User owner, boolean isPublic) {
        Box b = new Box();
        b.setOwner(owner);
        b.setPublic(isPublic);
        return b;
    }

    private BoxComment comment(User author, Box box) {
        BoxComment c = new BoxComment();
        c.setAuthor(author);
        c.setBox(box);
        return c;
    }


    @Nested
    class ReadAllTests{
        @Test
        void readAllOnEmptyRepository(){
            Mockito.when(boxCommentRepository.findAll()).thenReturn(Collections.emptyList());
            List<BoxComment> comments = boxCommentService.readAll();
            assertTrue(comments.isEmpty());
        }
    }


    @Nested
    class ReadTests {
        @Test
        void shouldThrowResourceNotFoundWhenCommentDoesNotExist(){ // ressource does not exist
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.read(1L, adminPrincipal));
        }

        @Test
        void shouldAllowRegisteredUserToReadCommentOfPublicBox(){
            // arrage
            User foreignUser = user(10L);
            Box publicBox = box(foreignUser, true);
            BoxComment foreignComment = comment(foreignUser, publicBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // act
            BoxComment result = boxCommentService.read(1L, registeredPrincipal);

            //assert
            assertEquals(result, foreignComment);
        }

        @Test
        void shouldThrowAccessDeniedWhenUserReadsCommentOfPrivateBox(){
            //arrange
            User foreignUser = user(10L);
            Box privateBox = box(foreignUser, false);
            BoxComment foreignComment = comment(foreignUser, privateBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // Act + Assert
            assertThrows(PermissionDeniedException.class, () -> boxCommentService.read(1L, registeredPrincipal));
        }


        @Test
        void shouldAllowAdminToReadCommentOfPrivateBox(){ // permission to read own ressource
            //arrange
            User foreignUser = user(10L);
            Box privateBox = box(foreignUser, false);
            BoxComment foreignComment = comment(foreignUser, privateBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // act
            BoxComment result = boxCommentService.read(1L, adminPrincipal);

            //assert
            assertEquals(result, foreignComment);
        }
    }






}
