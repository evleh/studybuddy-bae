package at.technikum.studybuddy.controller;

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
        return null;
    }

    @GetMapping("/{id}")
    public BoxComment getBoxCommentById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public BoxComment createBoxComment(@Valid @RequestBody BoxComment boxComment) {
        return boxComment;
    }

    @PutMapping("/{id}")
    public BoxComment updateBoxComment(@PathVariable Long id, @Valid @RequestBody BoxComment boxComment) {
        if (boxComment.getId().equals(id) && id != 0) {
            return boxComment;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ids in url and body params don't match.");
        }
    }

    @DeleteMapping("/{id}")
    public Long deleteBoxCommentById(@PathVariable Long id) {
        return id;
    }

}
