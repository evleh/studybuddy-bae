package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.RoleTypes;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public BoxService(BoxRepository boxRepository, UserRepository userRepository, UserService userService) {
        this.boxRepository = boxRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RolesAllowed(RoleTypes.ADMIN)
    public List<Box> readAll() {
        return boxRepository.findAll();
    }

    public Box read(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Box create(BoxDto boxDto) {
        // todo remove ownerId from boxDto??
        Optional<User> owner = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(owner.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Box box = new Box(boxDto.getTitle(), boxDto.getDescription(), boxDto.getPublic(), owner.get());
        return boxRepository.save(box);
    }

    public Box update(Long id, BoxDto boxDto) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return boxRepository.save(box.updateFromBoxDto(boxDto));
    }

    // change to return dto, to (hopefully) avoid the org.hibernate.LazyInitializationException
    public BoxDto delete(Long id) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BoxDto boxDto = new BoxDto(box);
        boxRepository.delete(box);
        return boxDto;
    }

    public boolean isPublicOrOwner(Box box, String username) {
        return box.getPublic() || box.getOwner().getUsername().equals(username);
    }

    public boolean isPublicOrOwnerIsCurrent(Box box) {
        return box.getPublic() || box.getOwner().getUsername().equals(
                userService.getUserNameOfCurrentUser()
        );
    }
}
