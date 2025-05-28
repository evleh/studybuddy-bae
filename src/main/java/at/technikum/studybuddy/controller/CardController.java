package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.entity.Card;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @GetMapping
    public List<Card> getAll(){
        return null;
    }

    @GetMapping("/{id}")
    public Card get(@PathVariable int id){
        return null;
    }

    @PostMapping
    public Card create(Card card){
        return null;
    }

    @PutMapping("/{id}")
    public Card update(@PathVariable int id, @RequestBody Card card){
        return null;
    }

    @DeleteMapping("/{id}")
    public Card delete(@PathVariable int id){
        return null;
    }



}
