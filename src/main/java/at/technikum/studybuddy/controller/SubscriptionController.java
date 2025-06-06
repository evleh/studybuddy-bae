package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.Subscription;
import at.technikum.studybuddy.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public List<Subscription> readAll() {
        return this.subscriptionService.readAll();
    }

    @GetMapping("/{id}")
    public Subscription read(@PathVariable int id) {
        return this.subscriptionService.read(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription create (@RequestBody @Valid Subscription subscription) {
        return this.subscriptionService.create(subscription);
    }

    @PutMapping("/{id}")
    public Subscription update(
            @PathVariable int id,
            @RequestBody Subscription subscription
    ) {
        return this.subscriptionService.update(id,subscription);
    }

    @DeleteMapping("/{id}")
    public Subscription delete (@PathVariable int id){
        return this.subscriptionService.delete(id);
    }
}
