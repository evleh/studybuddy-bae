package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.service.BoxCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/boxcomments")
public class BoxCommentController {

    private final BoxCommentService boxCommentService;

    BoxCommentController(BoxCommentService boxCommentService) {
        this.boxCommentService = boxCommentService;
    }

    @GetMapping
    public List<BoxCommentDto> readAll() {
        return this.boxCommentService.readAll().stream().map(BoxCommentDto::new).toList();
    }

    @GetMapping("/{id}")
    public BoxCommentDto readById(@PathVariable Long id) {
        return new BoxCommentDto(this.boxCommentService.read(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxCommentDto create(@Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.create(boxCommentDto));
    }

    @PutMapping("/{id}")
    public BoxCommentDto update(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.update(id, boxCommentDto));
    }

    @DeleteMapping("/{id}")
    public BoxCommentDto delete(@PathVariable Long id) {
        return this.boxCommentService.delete(id);
    }

}
