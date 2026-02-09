package at.technikum.studybuddy.controller;


import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<CardDto> readAll(){
        return this.cardService.readAll().stream().map(CardDto::new).toList();
    }

    // todo PostAuthorize
    @GetMapping("/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getBox().getOwner().getId().equals(authentication.principal.id)")
    public CardDto read(@PathVariable Long id){
        return new CardDto(this.cardService.read(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 202
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto create(@RequestBody @Valid CardDto cardDto){
        return new CardDto(this.cardService.create(cardDto));
    }

    // ToDo: Achtung hier nicht einfach übernehmen. In service wird save-methode aufgerufen.
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto update(@PathVariable Long id, @RequestBody @Valid CardDto cardDto){
        return new CardDto(this.cardService.update(id, cardDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto delete(@PathVariable Long id){
        return this.cardService.delete(id);
    }



}
