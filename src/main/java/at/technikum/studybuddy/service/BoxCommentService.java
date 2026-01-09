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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxComment create(BoxCommentDto boxCommentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal user)) {
            throw new PermissionDeniedException();
        }

        User author = userRepository.findByUsername(auth.getName())
                .orElseThrow(ResourceNotFoundException::new);

        Box box = boxRepository.findById(boxCommentDto.getBoxId())
                .orElseThrow(ResourceNotFoundException::new);

        if (!box.getPublic() || !box.getOwner().getId().equals(user.getId())) {
            throw new PermissionDeniedException();
        }

        BoxComment comment = new BoxComment(box, author, boxCommentDto.getText());
        return boxCommentRepository.save(comment);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BoxComment> readAll() {
        return this.boxCommentRepository.findAll();
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getAuthor().getId().equals(authentication.principal.id)")
    public BoxComment read(Long id) {
        return this.boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    //ML2: tested for owner
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxComment update(Long id, BoxCommentDto boxCommentDto) {
        BoxComment boxComment = read(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal user)) {
            throw new PermissionDeniedException();
        }

        User author = boxComment.getAuthor();
        if ( !author.getId().equals(user.getId()) && !author.isAdmin() )  {
            throw new PermissionDeniedException();
        }

        boxComment.setText(boxCommentDto.getText());
        return boxCommentRepository.save(boxComment);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BoxCommentDto delete(Long id) {
        BoxComment boxComment = boxCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        BoxCommentDto boxCommentDto = new BoxCommentDto(boxComment);
        boxCommentRepository.delete(boxComment);

        return boxCommentDto;
    }

}
