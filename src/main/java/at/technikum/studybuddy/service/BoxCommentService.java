package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxCommentService {

    private final BoxCommentRepository boxCommentRepository;
    public BoxCommentService(BoxCommentRepository boxCommentRepository) {
        this.boxCommentRepository = boxCommentRepository;
    }

    public BoxComment createBoxComment(BoxCommentDto boxCommentDto) {
        BoxComment boxComment = new BoxComment();
        boxComment.setText(boxCommentDto.getText());
        return boxCommentRepository.save(boxComment);
    }

    public List<BoxComment> getAllBoxComments() {
        return this.boxCommentRepository.findAll();
    }

    public BoxComment getBoxCommentById(Long id) {
        return this.boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        /* note:
          ResourceNotFoundException::new
          is an ide-suggest replacement for () -> new ResourceNotFoundException
          which is shorthand for if(..isempty()) ... etc
        */
    }
}
