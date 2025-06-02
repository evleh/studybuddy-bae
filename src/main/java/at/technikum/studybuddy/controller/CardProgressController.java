package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.CardProgress;
import at.technikum.studybuddy.service.CardProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardProgresses")
public class CardProgressController {
    private final CardProgressService cardProgressService;

    public CardProgressController(CardProgressService cardProgressService) {
        this.cardProgressService = cardProgressService;
    }

    @GetMapping
    public List<CardProgress> readAll(){
        return cardProgressService.readAll();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{id}")
    public CardProgress read(@PathVariable long id){
        return cardProgressService.read(id);
    }

    @PostMapping
    public CardProgress create(@RequestBody CardProgress cardProgress){
        return this.cardProgressService.create(cardProgress);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/{id}")
    public CardProgress update(@PathVariable long id, @RequestBody CardProgress cardProgress){
        return this.cardProgressService.update(id, cardProgress);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/{id}")
    public CardProgress delete(@PathVariable long id){
        return this.cardProgressService.delete(id);
    }

}
