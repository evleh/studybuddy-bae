package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.service.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAll(){
        return this.cardService.getAll();
    }

    @GetMapping("/{id}")
    public Card get(@PathVariable long id){
        return this.cardService.get(id);
    }

    @PostMapping
    public Card create(Card card){
        return this.cardService.create(card);
    }

    @PutMapping("/{id}")
    public Card update(@PathVariable long id, @RequestBody Card card){
        return this.cardService.update(id, card);
    }

    @DeleteMapping("/{id}")
    public Card delete(@PathVariable long id){
        return this.cardService.delete(id);
    }



}
