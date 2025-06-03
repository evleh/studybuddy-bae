package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> readAll(){
        return this.cardService.readAll();
    }

    @GetMapping("/{id}")
    public Card read(@PathVariable long id){
        return this.cardService.read(id);
    } // wirft 404

    @ResponseStatus(HttpStatus.CREATED) // 202
    @PostMapping
    public Card create(@RequestBody @Valid Card card){
        return this.cardService.create(card);
    }

    // ToDo: Achtung hier nicht einfach übernehmen. In service wird save-methode aufgerufen.
    @PutMapping("/{id}")
    public Card update(@PathVariable long id, @RequestBody @Valid Card card){
        return this.cardService.update(id, card);
    }

    @DeleteMapping("/{id}")
    public Card delete(@PathVariable long id){
        return this.cardService.delete(id);
    } // wirft 404



}
