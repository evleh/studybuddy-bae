package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.dto.UserDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.service.UserService;
import jakarta.annotation.security.RolesAllowed;
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
    public UserDto read(@PathVariable long id) {
        return this.userService.read(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create (@RequestBody @Valid Registration registration) {
        return this.userService.register(registration);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable long id,
            @RequestBody User user
    ) {
        return this.userService.update(id,user);
    }

    @DeleteMapping("/{id}")
    public User delete (@PathVariable int id){
        return userService.delete(id);
    }
}
