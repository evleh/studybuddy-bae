package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.service.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/box")
public class BoxController {

    private final BoxService boxService;

    BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public List<Box> readAllBoxes() {
        return boxService.readAllBoxes();
    }

    @GetMapping("/{id}")
    public Box readBoxById(@PathVariable Long id) { return boxService.readBoxById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Box createBox(@Valid @RequestBody BoxDto  boxDto) {
        return this.boxService.createBox(boxDto);
    }


    @PutMapping("/{id}")
    public Box updateBox(@PathVariable Long id, @Valid @RequestBody BoxDto boxDto) {
        return boxService.updateBox(id,boxDto);
    }

    @DeleteMapping("/{id}")
    public Box deleteBoxById(@PathVariable Long id) {
        return boxService.deleteBox(id);
    }
}
