package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.service.BoxCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxcomment")
public class BoxCommentController {

    private final BoxCommentService boxCommentService;

    BoxCommentController(BoxCommentService boxCommentService) {
        this.boxCommentService = boxCommentService;
    }

    @GetMapping
    public List<BoxCommentDto> getBoxComments() {
        return this.boxCommentService.getAllBoxComments().stream().map(BoxCommentDto::new).toList();
    }

    @GetMapping("/{id}")
    public BoxCommentDto getBoxCommentById(@PathVariable Long id) {
        return new BoxCommentDto(this.boxCommentService.getBoxCommentById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxCommentDto createBoxComment(@Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.createBoxComment(boxCommentDto));
    }

    @PutMapping("/{id}")
    public BoxCommentDto updateBoxComment(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.updateBoxComment(id, boxCommentDto));
    }

    @DeleteMapping("/{id}")
    public BoxCommentDto deleteBoxCommentById(@PathVariable Long id) {
        return new BoxCommentDto(this.boxCommentService.deleteBoxComment(id));
    }

}
