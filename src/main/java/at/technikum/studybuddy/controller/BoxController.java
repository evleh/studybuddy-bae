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

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public List<BoxDto> readAll() {
        return boxService.readAll().stream().map(BoxDto::new).toList();
    }

    @GetMapping("/{id}")
    public BoxDto read(@PathVariable Long id) {
        return new BoxDto(boxService.read(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxDto create(@Valid @RequestBody BoxDto  boxDto) {
        return new BoxDto(this.boxService.create(boxDto));
    }


    @PutMapping("/{id}")
    public BoxDto update(@PathVariable Long id, @Valid @RequestBody BoxDto boxDto) {
        return new BoxDto(boxService.update(id,boxDto));
    }

    @DeleteMapping("/{id}")
    public BoxDto delete(@PathVariable Long id) {
        return boxService.delete(id);
    }
}
