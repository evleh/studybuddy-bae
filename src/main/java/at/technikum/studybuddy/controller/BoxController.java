package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.service.BoxService;
import jakarta.validation.Valid;
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
    public List<Box> getAllBoxes() {
        return null;
    }

    @GetMapping("/{id}")
    public Box getBoxById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public Box createBox(@Valid @RequestBody Box  box) {
        return box;
    }

    @PutMapping("/{id}")
    public Box updateBox(@PathVariable Long id, @Valid @RequestBody Box box) {
        return box;
    }

    @DeleteMapping("/{id}")
    public Long deleteBoxById(@PathVariable Long id) {
        return id;
    }
}
