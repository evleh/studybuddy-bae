package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    void readAllOnEmptyRepository(){
        Mockito.when(boxCommentRepository.findAll()).thenReturn(Collections.emptyList());

        List<BoxComment> comments = boxCommentService.readAll();
        System.out.println(comments);
        assertTrue(comments.isEmpty());
    }
}
