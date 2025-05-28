package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.Subscription;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.service.SubscriptionService;
import at.technikum.studybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {


    @GetMapping
    public List<Subscription> readAll() {
        return null;
    }

    @GetMapping("/{id}")
    public Subscription read(@PathVariable int id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription create (@RequestBody @Valid Subscription subscription) {
        return null;
    }

    @PutMapping("/{id}")
    public Subscription update(
            @PathVariable int id,
            @RequestBody Subscription subscription
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Subscription delete (@PathVariable int id){
        return null;
    }
}
