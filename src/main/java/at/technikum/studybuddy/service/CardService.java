package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getBox().getOwner().getId().equals(authentication.principal.id)")
    public Card read(Long id) throws ResourceNotFoundException{
        Optional<Card> cardOptional = this.cardRepository.findById(id);
        if(cardOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return cardOptional.get();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Card create(CardDto cardDto){
        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipalOrAdmin(cardDto)){
            throw new PermissionDeniedException();
        }

        Card card = new Card();
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        card.setBox(boxService.read(cardDto.getBoxId()));

        return this.cardRepository.save(card);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Card update(Long id, CardDto cardDto){
        // Eventuell InputMismatchException wenn id-Paramater und card-id nicht übereinstimmen
        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipalOrAdmin(cardDto)){
            throw new PermissionDeniedException();
        }

        Optional<Card> cardOptional = this.cardRepository.findById(id);
        if(cardOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        // todo: logic does not work !!!
        Card card = read(id);
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        return cardRepository.save(card);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public Card delete(Long id) throws ResourceNotFoundException{
        Card card = this.cardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipalOrAdmin(card)){
            throw new PermissionDeniedException();
        }
        this.cardRepository.deleteById(id);
        return card;
    }

    public boolean isBoxOwnerPrincipalOrAdmin(CardDto cardDto){
        Box parentBox = boxService.readInternal(cardDto.getBoxId());
        User boxOwner = parentBox.getOwner();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof UserPrincipal userPrincipal){
            Long userPrincipalId = userPrincipal.getId();
            return boxOwner.getId().equals(userPrincipalId) || boxOwner.getRole().equals("ROLE_ADMIN");
        }
        return false;
    }

    public boolean isBoxOwnerPrincipalOrAdmin(Card card){
        return this.isBoxOwnerPrincipalOrAdmin(new CardDto(card));
    }
}
