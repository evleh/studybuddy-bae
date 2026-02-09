package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.service.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BoxDto> readAll() {
        return boxService.readAll().stream().map(BoxDto::new).toList();
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getPublic() || returnObject.getOwnerId().equals(principal.id)")
    public BoxDto read(@PathVariable Long id) {
        return new BoxDto(boxService.read(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxDto create(@Valid @RequestBody BoxDto  boxDto) {
        return new BoxDto(this.boxService.create(boxDto));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxDto update(@PathVariable Long id, @Valid @RequestBody BoxDto boxDto) {
        return new BoxDto(boxService.update(id,boxDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public BoxDto delete(@PathVariable Long id) {
        return boxService.delete(id);
    }
}
