package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.security.UserPrincipal;
import at.technikum.studybuddy.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CardDto> readAll(@AuthenticationPrincipal UserPrincipal requester){
        return this.cardService.readAll(requester).stream().map(CardDto::new).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto read(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal requester){
        return new CardDto(this.cardService.read(id, requester));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 202
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto create(@RequestBody @Valid CardDto cardDto){
        return new CardDto(this.cardService.create(cardDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto update(@PathVariable Long id, @RequestBody @Valid CardDto cardDto,
                          @AuthenticationPrincipal UserPrincipal requester){
        return new CardDto(this.cardService.update(id, cardDto, requester));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto delete(@PathVariable Long id){
        return this.cardService.delete(id);
    }



}
