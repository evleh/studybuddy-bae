package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> readAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User read(@PathVariable long id) {
        return this.userService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create (@RequestBody @Valid User user) {
        return this.userService.create(user);
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
