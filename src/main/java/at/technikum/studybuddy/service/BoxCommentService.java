package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.entity.BoxComment;
    import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxCommentService {

    private final BoxCommentRepository boxCommentRepository;
    private final BoxService boxService;
    public BoxCommentService(BoxCommentRepository boxCommentRepository, BoxService boxService) {
        this.boxCommentRepository = boxCommentRepository;
        this.boxService = boxService;
    }

    public BoxComment createBoxComment(BoxCommentDto boxCommentDto) {
        BoxComment boxComment = new BoxComment();
        boxComment.setText(boxCommentDto.getText());
        boxComment.setBox(boxService.readBoxById(boxCommentDto.getBoxId())); // if not found, boxService should throw.
        return boxCommentRepository.save(boxComment);
    }

    public List<BoxComment> readAllBoxComments() {
        return this.boxCommentRepository.findAll();
    }

    public BoxComment readBoxCommentById(Long id) {
        return this.boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        /* note:
          ResourceNotFoundException::new
          is an ide-suggest replacement for () -> new ResourceNotFoundException
          which is shorthand for if(..isempty()) ... etc
        */
    }

    public BoxComment updateBoxComment(Long id, BoxCommentDto boxCommentDto) {
        BoxComment boxComment = readBoxCommentById(id);
        boxComment.setText(boxCommentDto.getText());
        return boxCommentRepository.save(boxComment);
    }

    public BoxComment deleteBoxComment(Long id) {
        Optional<BoxComment> boxComment = this.boxCommentRepository.findById(id);
        boxComment.orElseThrow(ResourceNotFoundException::new);
        // comment for line above: orElseThrow does not return an optional, so no chaining :(

        try {
            // ide suggests; suspect makes it easier to change pks later?
            // docs seem to say delete will look up with Ids from the object here
            boxCommentRepository.delete(boxComment.get());
        } catch (IllegalArgumentException e) {
            // in case it got deleted by someone else in between?
            // might be too much, idk.
            throw new ResourceNotFoundException();
        }
        // assuming everything went smooth, then:
        return boxComment.get();

    }


}
