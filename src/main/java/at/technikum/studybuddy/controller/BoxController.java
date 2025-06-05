package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.service.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/boxes")
public class BoxController {

    private final BoxService boxService;

    BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public List<BoxDto> readAll() {
        return boxService.readAllBoxes().stream().map(BoxDto::new).toList();
    }

    @GetMapping("/{id}")
    public BoxDto readById(@PathVariable Long id) { return new BoxDto(boxService.readBoxById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxDto create(@Valid @RequestBody BoxDto  boxDto) {
        return new BoxDto(this.boxService.createBox(boxDto));
    }


    @PutMapping("/{id}")
    public BoxDto update(@PathVariable Long id, @Valid @RequestBody BoxDto boxDto) {
        return new BoxDto(boxService.updateBox(id,boxDto));
    }

    @DeleteMapping("/{id}")
    public BoxDto deleteById(@PathVariable Long id) {
        return boxService.deleteBox(id);
    }
}
