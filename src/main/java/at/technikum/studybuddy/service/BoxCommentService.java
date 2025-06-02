package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import org.springframework.stereotype.Service;

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
}
