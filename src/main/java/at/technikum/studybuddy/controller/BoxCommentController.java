package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.service.BoxCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BoxCommentDto> readAll() {
        return this.boxCommentService.readAll().stream().map(BoxCommentDto::new).toList();
    }

    @GetMapping("/{id}") // todo fix authentication
    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getAuthorId().equals(principal.id)")
    public BoxCommentDto read(@PathVariable Long id) {
        return new BoxCommentDto(this.boxCommentService.read(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxCommentDto create(@Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.create(boxCommentDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxCommentDto update(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto) {
        return new BoxCommentDto(this.boxCommentService.update(id, boxCommentDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BoxCommentDto delete(@PathVariable Long id) {
        return this.boxCommentService.delete(id);
    }

}
