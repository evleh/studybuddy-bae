package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.security.UserPrincipal;
import at.technikum.studybuddy.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<CardDto> readAll(@AuthenticationPrincipal UserPrincipal requester){
        return this.cardService.readAll(requester).stream().map(CardDto::new).toList();
    }

    @GetMapping("/{id}")
    public CardDto read(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal requester){
        return new CardDto(this.cardService.read(id, requester));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 202
    public CardDto create(@RequestBody @Valid CardDto cardDto,
                          @AuthenticationPrincipal UserPrincipal requester){
        return new CardDto(this.cardService.create(cardDto, requester));
    }

    @PutMapping("/{id}")
    public CardDto update(@PathVariable Long id, @RequestBody @Valid CardDto cardDto,
                          @AuthenticationPrincipal UserPrincipal requester){
        return new CardDto(this.cardService.update(id, cardDto, requester));
    }

    @DeleteMapping("/{id}")
    public CardDto delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal requester){
        return this.cardService.delete(id, requester);
    }



}
