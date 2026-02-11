package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.security.UserPrincipal;
import at.technikum.studybuddy.service.BoxCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxCommentDto read(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        return new BoxCommentDto(this.boxCommentService.read(id, user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxCommentDto create(@Valid @RequestBody BoxCommentDto boxCommentDto, @AuthenticationPrincipal UserPrincipal user) {
        return new BoxCommentDto(this.boxCommentService.create(boxCommentDto, user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxCommentDto update(@PathVariable Long id, @Valid @RequestBody BoxCommentDto boxCommentDto,
                                @AuthenticationPrincipal UserPrincipal user) {
        return new BoxCommentDto(this.boxCommentService.update(id, boxCommentDto, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BoxCommentDto delete(@PathVariable Long id) {
        // only admin is allowed to delete to prevent deletion malicious comments by trolls
        return this.boxCommentService.delete(id);
    }

}
