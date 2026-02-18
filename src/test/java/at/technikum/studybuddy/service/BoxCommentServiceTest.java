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


/*
    What could be better:
        1) Move nested classes to own file
        2) Create principal for every testcase (instead of defining it as attribute)
 */

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
            User foreignUser = TestDataFactory.user(10L);
            Box publicBox = TestDataFactory.box(5L, foreignUser, true);
            BoxComment foreignComment = TestDataFactory.comment(foreignUser, publicBox);
            foreignComment.setId(1L);
            Mockito.when(boxCommentRepository.findById(any())).thenReturn(Optional.of(foreignComment));

            // act
            BoxComment result = boxCommentService.read(foreignComment.getId(), registeredPrincipal);

            //assert
            assertEquals(result, foreignComment);
        }

        @Test
        void shouldThrowAccessDeniedWhenUserReadsCommentOfPrivateBox(){
            //arrange
            User foreignUser = TestDataFactory.user(10L);
            Box privateBox = TestDataFactory.box(5L, foreignUser, false);
            BoxComment foreignComment = TestDataFactory.comment(foreignUser, privateBox);
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(foreignComment));

            // Act + Assert
            assertThrows(PermissionDeniedException.class, () -> boxCommentService.read(1L, registeredPrincipal));
        }


        @Test
        void shouldAllowAdminToReadCommentOfPrivateBox(){ // permission to read own ressource
            //arrange
            User foreignUser = TestDataFactory.user(10L);
            Box privateBox = TestDataFactory.box(5L, foreignUser, false);
            BoxComment foreignComment = TestDataFactory.comment(foreignUser, privateBox);
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
            User user = TestDataFactory.user(10L);
            Box box = TestDataFactory.box(5L,user, false);
            BoxComment comment = TestDataFactory.comment(user, box);
            BoxCommentDto dto = new BoxCommentDto(comment);
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenThrow(new ResourceNotFoundException());
            // act + assert
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.create(dto, registeredPrincipal));
        }

        @Test
        void shouldThrowWhenBoxIsNotFound(){
            // arrange
            User user = TestDataFactory.user(10L);
            BoxCommentDto dto = TestDataFactory.boxCommentDto(5L);
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenReturn(Optional.of(user));
            Mockito.when(boxRepository.findById(dto.getBoxId())).thenThrow(new ResourceNotFoundException());

            // act + assert
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.create(dto, registeredPrincipal));
        }

        @Test
        void shouldAllowCommentOnPublicBox(){
            // Arrange
            User commentCreator = TestDataFactory.user(registeredPrincipal.getId());
            User boxOwner = TestDataFactory.user(2L);
            Box box = TestDataFactory.box(5L, boxOwner, true);
            BoxComment boxComment = new BoxComment(box, commentCreator, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);


            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));
            // When save is called return the Object that was used as argument for the method call.
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, registeredPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldAllowAdminToCommentOnPrivateBox(){
            // Arrange
            // arrange: comment author
            User commentCreator = TestDataFactory.user(adminPrincipal.getId());
            commentCreator.setAdmin(true);
            Mockito.when(userRepository.findByUsername(adminPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = TestDataFactory.user(2L);
            Box box = TestDataFactory.box(5L, boxOwner, false);

            BoxComment boxComment = new BoxComment(box, boxOwner, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // arrange: saving the comment to repository
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, adminPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }


        @Test
        void shouldAllowOwnerToCommentOnOwnPrivateBox(){
            // Arrange
            // arrange: comment author
            User commentCreator = TestDataFactory.user(registeredPrincipal.getId());
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = commentCreator;
            Box box = TestDataFactory.box(5L, boxOwner, false);

            BoxComment boxComment = new BoxComment(box, commentCreator, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // arrange: saving the comment to repository
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
            // act
            BoxComment result = boxCommentService.create(boxCommentDto, registeredPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldThrowWhenRegularUserCommentsOnPrivateBox(){
            // Arrange
            // arrange: comment author
            User commentCreator = TestDataFactory.user(registeredPrincipal.getId());
            Mockito.when(userRepository.findByUsername(registeredPrincipal.getUsername())).thenReturn(Optional.of(commentCreator));

            // arrange: box & comment
            User boxOwner = TestDataFactory.user(5L);
            Box box = TestDataFactory.box(5L, boxOwner, false);

            BoxComment boxComment = new BoxComment(box, commentCreator, "lalalla");
            BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
            Mockito.when(boxRepository.findById(boxCommentDto.getBoxId())).thenReturn(Optional.of(box));

            // act & assert
            assertThrows(PermissionDeniedException.class, () -> boxCommentService.create(boxCommentDto, registeredPrincipal));
        }

    }

    @Nested
    class UpdateTests{

        @Test
        void shouldAllowAdminToUpdateAnyComment(){
            // arrange
            User owner = TestDataFactory.user(5L);
            Box box = TestDataFactory.box(2L, owner, true);
            BoxComment boxComment = new BoxComment(box, owner, "lalala");
            boxComment.setId(1L);

            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(boxComment));
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // act
            BoxComment result = boxCommentService.update(1L, new BoxCommentDto(boxComment), adminPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldAllowUserToUpdateOwnComment(){
            // arrange
            User owner = TestDataFactory.user(registeredPrincipal.getId());
            Box box = TestDataFactory.box(2L, owner, false);
            BoxComment boxComment = new BoxComment(box, owner, "lalala");
            boxComment.setId(1L);

            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(boxComment));
            Mockito.when(boxCommentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // act
            BoxComment result = boxCommentService.update(1L, new BoxCommentDto(boxComment), registeredPrincipal);

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).save(any());
        }

        @Test
        void shouldThrowWhenUserUpdatesForeignComment(){
            // arrange
            User owner = TestDataFactory.user(5L);
            Box box = TestDataFactory.box(2L, owner, true);
            BoxComment boxComment = new BoxComment(box, owner, "lalala");
            boxComment.setId(1L);

            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.of(boxComment));

            // act & assert
            assertThrows(PermissionDeniedException.class, () ->
                    boxCommentService.update(1L, new BoxCommentDto(boxComment), registeredPrincipal));
        }

    }

    @Nested
    class DeleteTests{

        @Test
        void shouldThrowWhenCommentDoesNotExist(){
            // arrange
            Mockito.when(boxCommentRepository.findById(1L)).thenReturn(Optional.empty());

            // act & assert
            assertThrows(ResourceNotFoundException.class, () -> boxCommentService.delete(1L));
        }

        @Test
        void shouldDeleteWhenCommentExists(){
            // arrange
            User author = TestDataFactory.user(1L);
            Box box = TestDataFactory.box(2L, author, true);
            BoxComment comment = TestDataFactory.comment(author, box);
            comment.setId(3L);
            Mockito.when(boxCommentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

            //act
            BoxCommentDto result = boxCommentService.delete(comment.getId());

            // assert
            assertNotNull(result);
            verify(boxCommentRepository).delete(any());


        }
    }






}
