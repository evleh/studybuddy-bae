package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.service.BoxCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/boxcomment")
public class BoxCommentController {

    private final BoxCommentService boxCommentService;

    BoxCommentController(BoxCommentService boxCommentService) {
        this.boxCommentService = boxCommentService;
    }

    @GetMapping
    public List<BoxComment> getBoxComments() {
        return this.boxCommentService.getAllBoxComments();
    }

    @GetMapping("/{id}")
    public BoxCommentDto getBoxCommentById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public BoxComment createBoxComment(@Valid @RequestBody BoxCommentDto boxCommentDto) {
        // returning dto or entity itself? currently no reason wrap it that I can see.
        return this.boxCommentService.createBoxComment(boxCommentDto);
    }

    @PutMapping("/{id}")
    public BoxCommentDto updateBoxComment(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto) {
        /* to be refactored to the service; but until service exists this is a workaround for a swagger behaviour */
        if (boxCommentDto.getId().equals(id) && id != 0) {
            return boxCommentDto;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ids in url and body params don't match.");
        }
    }

    @DeleteMapping("/{id}")
    public Long deleteBoxCommentById(@PathVariable Long id) {
        return id;
    }

}
