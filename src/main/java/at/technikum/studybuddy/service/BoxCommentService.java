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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxCommentService {

    private final BoxCommentRepository boxCommentRepository;
    private final BoxRepository boxRepository;
    private final UserRepository userRepository;

    public BoxCommentService(BoxCommentRepository boxCommentRepository, BoxRepository boxRepository, UserRepository userRepository) {
        this.boxCommentRepository = boxCommentRepository;
        this.boxRepository = boxRepository;
        this.userRepository = userRepository;
    }

    public BoxComment create(BoxCommentDto boxCommentDto, UserPrincipal user) {

        User author = userRepository.findByUsername(user.getUsername())
                .orElseThrow(ResourceNotFoundException::new);

        Box box = boxRepository.findById(boxCommentDto.getBoxId())
                .orElseThrow(ResourceNotFoundException::new);

        if (box.getPublic() || box.getOwner().getId().equals(user.getId()) || author.isAdmin()) {
            BoxComment comment = new BoxComment(box, author, boxCommentDto.getText());
            return boxCommentRepository.save(comment);
        } else {
            throw new PermissionDeniedException();
        }

    }

    public List<BoxComment> readAll() {
        return this.boxCommentRepository.findAll();
    }

    public BoxComment read(Long id, UserPrincipal user) {
        BoxComment boxComment = this.boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        Long authorId = boxComment.getAuthor().getId();
        boolean isPublic = boxComment.getBox().getPublic();
        // extract from UserPrincipal because user and author are not necessarily the same person
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));;
        if(isAdmin || isPublic || user.getId().equals(authorId)){
            return boxComment;
        } else {
            throw new PermissionDeniedException();
        }
    }

    public BoxComment update(Long id, BoxCommentDto boxCommentDto, UserPrincipal user) {
        BoxComment boxComment = read(id, user);

        User author = boxComment.getAuthor();

        // extract from UserPrincipal because user and author are not necessarily the same person
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));;

        if (!author.getId().equals(user.getId()) && !isAdmin )  {
            throw new PermissionDeniedException();
        }

        // only visibility can be changed for box comments
        boxComment.setText(boxCommentDto.getText());
        return boxCommentRepository.save(boxComment);
    }

    public BoxCommentDto delete(Long id) {
        BoxComment boxComment = boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
        boxCommentRepository.delete(boxComment);

        return boxCommentDto;
    }

}
