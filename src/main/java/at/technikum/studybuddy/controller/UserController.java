package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.dto.UserDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.dto.UserDtoPublicInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDtoPrivilegedInfo::new)
                .map(UserDtoPrivilegedInfo::downCastToAbstract)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable Long id) {
        return new UserDtoPrivilegedInfo(this.userService.read(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create (@RequestBody @Valid Registration registration) {
        return new UserDtoPrivilegedInfo(this.userService.register(registration));
    }

    @PutMapping("/{id}")
    public UserDto update(
            @PathVariable Long id,
            @Valid @RequestBody UserDtoPrivilegedInfo userDto
    ) {
        return new UserDtoPrivilegedInfo(this.userService.update(id,userDto));
    }

    @DeleteMapping("/{id}")
    public UserDto delete (@PathVariable Long id){
        return new UserDtoPublicInfo(userService.delete(id));
    }
}
