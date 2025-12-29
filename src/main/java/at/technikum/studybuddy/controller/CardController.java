package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.service.CardService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDto> readAll(){
        return this.cardService.readAll().stream().map(CardDto::new).toList();
    }

    @GetMapping("/{id}")
    public CardDto read(@PathVariable long id){
        return new CardDto(this.cardService.read(id));
    }

    @ResponseStatus(HttpStatus.CREATED) // 202
    @PostMapping
    public CardDto create(@RequestBody @Valid CardDto cardDto){
        return new CardDto(this.cardService.create(cardDto));
    }

    // ToDo: Achtung hier nicht einfach übernehmen. In service wird save-methode aufgerufen.
    @PutMapping("/{id}")
    public CardDto update(@PathVariable long id, @RequestBody @Valid CardDto cardDto){
        return new CardDto(this.cardService.update(id, cardDto));
    }

    @DeleteMapping("/{id}")
    public CardDto delete(@PathVariable long id){
        return this.cardService.delete(id);
    } // wirft 404



}
