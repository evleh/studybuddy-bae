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


    @GetMapping
    public List<User> readAll() {
        return null;
    }

    @GetMapping("/{id}")
    public User read(@PathVariable int id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create (@RequestBody @Valid User user) {
        return null;
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable int id,
            @RequestBody User user
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public User delete (@PathVariable int id){
        return null;
    }
}
