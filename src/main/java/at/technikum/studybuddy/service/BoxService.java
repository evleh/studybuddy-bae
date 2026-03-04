package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipal;
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

    public List<Box> readAll() {
        return boxRepository.findAll();
    }

    public Box read(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Box readInternal(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


    public Box create(BoxDto boxDto, UserPrincipal user) {
        Optional<User> owner = this.userRepository.findByUsername(user.getUsername());
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
    public BoxDto delete(Long id, UserPrincipal user) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        Long userId = user.getId();

        if(!box.getOwner().getId().equals(userId) && !user.isStudyBuddyAdmin() ) {
            throw new PermissionDeniedException();
        }

        BoxDto boxDto = new BoxDto(box);
        boxRepository.delete(box);
        return boxDto;
    }

    public List<Box> readPublicBoxes() {
        return this.boxRepository.findBoxesByIsPublicIsTrue();
    }

    public List<Box> readBoxesOfUser(Long userId) {

        return this.boxRepository.findBoxesByOwner(
                this.userRepository.findById(userId).orElseThrow()
        );
    }

}
