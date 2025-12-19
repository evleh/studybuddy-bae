package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.service.BoxCommentService;
import at.technikum.studybuddy.service.BoxService;
import at.technikum.studybuddy.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/boxcomments")
public class BoxCommentController {

    private final BoxCommentService boxCommentService;
    private final UserService userService;
    private final BoxService boxService;

    BoxCommentController(BoxCommentService boxCommentService, UserService userService, BoxService boxService) {
        this.boxCommentService = boxCommentService;
        this.userService = userService;
        this.boxService = boxService;
    }

    @GetMapping
    @RolesAllowed(RoleTypes.ADMIN)
    public List<BoxCommentDto> readAll() {
        return this.boxCommentService.readAllBoxComments().stream().map(BoxCommentDto::new).toList();
    }

    @GetMapping("/{id}")
    public BoxCommentDto readById(@PathVariable Long id) {
        return new BoxCommentDto(this.boxCommentService.readBoxCommentById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxCommentDto create(@Valid @RequestBody BoxCommentDto boxCommentDto) {
        if (this.boxCommentService.isCurrentUserAllowedToCommentOnBoxWithId(boxCommentDto.getBoxId())) {
            return new BoxCommentDto(this.boxCommentService.createBoxComment(boxCommentDto));
        } else {
            throw new PermissionDeniedException();
        }
    }

    @PutMapping("/{id}")
    public BoxCommentDto update(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.updateBoxComment(id, boxCommentDto));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(RoleTypes.ADMIN)
    public BoxCommentDto deleteById(@PathVariable Long id) {
        return this.boxCommentService.deleteBoxComment(id);
    }

}
