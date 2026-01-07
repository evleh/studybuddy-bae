package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoxService boxService;

    public CardService(CardRepository cardRepository, BoxService boxService) {

        this.cardRepository = cardRepository;
        this.boxService = boxService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Card> readAll(){
        return this.cardRepository.findAll();
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getBox().getOwner().getId() == authentication.principal.id")
    public Card read(long id) throws ResourceNotFoundException{
        Optional<Card> cardOptional = this.cardRepository.findById(id);
        if(cardOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return cardOptional.get();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Card create(CardDto cardDto){
        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipal(cardDto)){
            throw new PermissionDeniedException();
        }

        Card card = new Card();
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        card.setBox(boxService.read(cardDto.getBoxId()));

        return this.cardRepository.save(card);
    }

    // todo talk about this. is this unsave ???????
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Card update(long id, CardDto cardDto){
        // Eventuell InputMismatchException wenn id-Paramater und card-id nicht übereinstimmen
        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipal(cardDto)){
            throw new PermissionDeniedException();
        }

        Optional<Card> cardOptional = this.cardRepository.findById(id);
        if(cardOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        Card card = read(id);
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        return cardRepository.save(card);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public CardDto delete(long id) throws ResourceNotFoundException{
        Optional<Card> cardOptional = this.cardRepository.findById(id);
        if(cardOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipal(cardOptional.get())){
            throw new PermissionDeniedException();
        }

        this.cardRepository.deleteById(id);
        return new CardDto(cardOptional.get());

    }

    public boolean isBoxOwnerPrincipal(CardDto cardDto){
        Box parentBox = boxService.read(cardDto.getBoxId());
        Long boxOwnerId = parentBox.getOwner().getId();
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userPrincipalId = userPrincipal.getId();
        return boxOwnerId.equals(userPrincipalId);
    }

    public boolean isBoxOwnerPrincipal(Card card){
        return this.isBoxOwnerPrincipal(new CardDto(card));
    }
}
