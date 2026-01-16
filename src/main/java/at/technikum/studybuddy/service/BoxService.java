package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final UserRepository userRepository;

    public BoxService(BoxRepository boxRepository, UserRepository userRepository) {
        this.boxRepository = boxRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Box> readAll() {
        return boxRepository.findAll();
    }

    // ML2: tested
    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getPublic() || authentication.principal.id.equals(returnObject.owner.getId())")
    public Box read(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Box readInternal(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


    //ML2: tested
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Box create(BoxDto boxDto) {
        // todo remove ownerId from boxDto??
        Optional<User> owner = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(owner.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Box box = new Box(boxDto.getTitle(), boxDto.getDescription(), boxDto.getPublic(), owner.get());
        return boxRepository.save(box);
    }

    //ML2: tested
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Box update(Long id, BoxDto boxDto) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userPrincipal.getId();

        // can't use PostAuthorize because returnObject is updated box i.e. changes are already saved when check happens
        if(!box.getOwner().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        return boxRepository.save(box.updateFromBoxDto(boxDto));
    }

    // change to return dto, to (hopefully) avoid the org.hibernate.LazyInitializationException
    // ML2: worked with owner, needs proper testing
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxDto delete(Long id) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userPrincipal.getId();

        // can't use PostAuthorize because returnObject is updated box i.e. changes are already saved when check happens
        if( !box.getOwner().getId().equals(userId) && !box.getOwner().isAdmin() ) {
            throw new PermissionDeniedException();
        }

        BoxDto boxDto = new BoxDto(box);
        boxRepository.delete(box);
        return boxDto;
    }

    public List<Box> readPublicBoxes() {
        return this.boxRepository.findBoxesByIsPublicIsTrue();
    }

}
