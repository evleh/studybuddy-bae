package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.repository.BoxRepository;
import at.technikum.studybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;


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

    public List<Box> readAllBoxes() {
        return boxRepository.findAll();
    }

    public Box readBoxById(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Box createBox(BoxDto boxDto) {
        Optional<User> owner = this.userRepository.findById(boxDto.getOwnerId());
        if(owner.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Box box = new Box(boxDto.getTitle(), boxDto.getDescription(), boxDto.getPublic(), owner.get());
        return boxRepository.save(box);
    }

    public Box updateBox(Long id, BoxDto boxDto) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return boxRepository.save(box.updateFromBoxDto(boxDto));
    }

    // change to return dto, to (hopefully) avoid the org.hibernate.LazyInitializationException
    public BoxDto deleteBox(Long id) {
        Box box = boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BoxDto boxDto = new BoxDto(box);
        boxRepository.delete(box);
        return boxDto;
    }

}
