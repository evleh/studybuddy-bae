package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxCommentDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


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

    private Box box(Long id, User owner, boolean isPublic) {
        Box b = new Box();
        b.setId(id);
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
            Box publicBox = box(5L, foreignUser, true);
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
            Box privateBox = box(5L, foreignUser, false);
            BoxComment foreignComment = comment(foreignUser, privateBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // Act + Assert
            assertThrows(PermissionDeniedException.class, () -> boxCommentService.read(1L, registeredPrincipal));
        }


        @Test
        void shouldAllowAdminToReadCommentOfPrivateBox(){ // permission to read own ressource
            //arrange
            User foreignUser = user(10L);
            Box privateBox = box(5L, foreignUser, false);
            BoxComment foreignComment = comment(foreignUser, privateBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // act
            BoxComment result = boxCommentService.read(1L, adminPrincipal);

            //assert
            assertEquals(result, foreignComment);
        }
    }

    @Nested
    class CreateTests{

        @Test
        void shouldThrowWhenAuthorNameIsNotFound(){
            // arrange
            User user = user(10L);
            Box box = box(5L,user, false);
            BoxComment comment = comment(user, box);
            BoxCommentDto dto = new BoxCommentDto(comment);
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenThrow(new ResourceNotFoundException());
            // act + assert
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.create(dto, registeredPrincipal));
        }

        @Test
        void shouldThrowWhenBoxIsNotFound(){
            // arrange
            User user = user(10L);
            BoxCommentDto dto = TestDataFactory.boxCommentDto(5L);
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenReturn(Optional.of(user));
            Mockito.when(boxRepository.findById(dto.getBoxId())).thenThrow(new ResourceNotFoundException());

            // act + assert
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.create(dto, registeredPrincipal));
        }

        @Test
        void shouldAllowCommentOnPublicBox(){
            // Arrange
            UserPrincipal commentCreatorPrincipal = TestPrincipalFactory.registeredUser(42L);
            User commentCreator = user(42L);
            User boxOwner = user(2L);
            Box box = TestDataFactory.box(5L, boxOwner, true);
            BoxComment boxComment = new BoxComment(box, boxOwner, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);


            Mockito.when(userRepository.findByUsername(commentCreatorPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));
            // When save is called return the Object that was used as argument for the method call.
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, commentCreatorPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldAllowAdminToCommentOnPrivateBox(){
            // Arrange
            // arrange: comment author
            UserPrincipal commentCreatorPrincipal = TestPrincipalFactory.admin();
            User commentCreator = user(commentCreatorPrincipal.getId());
            commentCreator.setAdmin(true);
            Mockito.when(userRepository.findByUsername(commentCreatorPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = user(2L);
            Box box = TestDataFactory.box(5L, commentCreator, false);

            BoxComment boxComment = new BoxComment(box, boxOwner, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // arrange: saving the comment to repository
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, commentCreatorPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }


        @Test
        void shouldAllowOwnerToCommentOnOwnPrivateBox(){
            // Arrange
            // arrange: comment author
            UserPrincipal commentCreatorPrincipal = TestPrincipalFactory.registeredUser(2L);
            User commentCreator = user(commentCreatorPrincipal.getId());
            Mockito.when(userRepository.findByUsername(commentCreatorPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = commentCreator;
            Box box = TestDataFactory.box(5L, boxOwner, false);

            BoxComment boxComment = new BoxComment(box, commentCreator, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // arrange: saving the comment to repository
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, commentCreatorPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldThrowWhenRegularUserCommentsOnPrivateBox(){
            // Arrange
            // arrange: comment author
            UserPrincipal commentCreatorPrincipal = TestPrincipalFactory.registeredUser(2L);
            User commentCreator = user(commentCreatorPrincipal.getId());
            Mockito.when(userRepository.findByUsername(commentCreatorPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = user(42L);
            Box box = TestDataFactory.box(5L, boxOwner, false);

            BoxComment boxComment = new BoxComment(box, commentCreator, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // act & assert
            assertThrows(PermissionDeniedException.class, () -> boxCommentService.create(boxCommentDto, commentCreatorPrincipal));
        }

    }






}
