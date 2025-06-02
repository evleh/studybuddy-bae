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

    @GetMapping("/{id}")
    public CardProgress read(@PathVariable long id){ // wirft 404
        return cardProgressService.read(id);
    }

    @ResponseStatus(HttpStatus.CREATED) // 202
    @PostMapping
    public CardProgress create(@RequestBody CardProgress cardProgress){
        return this.cardProgressService.create(cardProgress);
    }


    @PutMapping("/{id}")
    public CardProgress update(@PathVariable long id, @RequestBody CardProgress cardProgress){ // wirft 404
        return this.cardProgressService.update(id, cardProgress);
    }


    @DeleteMapping("/{id}")
    public CardProgress delete(@PathVariable long id){ // wirft 404
        return this.cardProgressService.delete(id);
    }

}
