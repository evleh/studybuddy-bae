package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.repository.BoxRepository;
import org.springframework.stereotype.Service;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;


import java.util.List;

@Service
public class BoxService {

    private final BoxRepository boxRepository;

    BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public List<Box> readAllBoxes() {
        return boxRepository.findAll();
    }

    public Box readBoxById(Long id) {
        return boxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Box createBox(BoxDto boxDto) {
        Box box = boxDto.makeAndGetBoxForCreation();
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
