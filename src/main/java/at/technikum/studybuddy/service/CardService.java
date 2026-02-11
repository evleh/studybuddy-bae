package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoxService boxService;

    public CardService(CardRepository cardRepository, BoxService boxService) {

        this.cardRepository = cardRepository;
        this.boxService = boxService;
    }

    public List<Card> readAll(UserPrincipal requestPrincipal) {

        boolean requesterIsAdmin = requestPrincipal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (requesterIsAdmin) {
            return this.cardRepository.findAll();
        } else {
            return new ArrayList<Card>();
        }

    }

    public Card read(Long id, UserPrincipal requester) throws ResourceNotFoundException{
        // refactor this: @PostAuthorize("hasRole('ROLE_ADMIN') || returnObject.getBox().getPublic() || returnObject.getBox().getOwner().getId().equals(principal.id)")

        boolean requesterIsAdmin = requester.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // need to know if the card exists even in the admin case, branch further down
        Optional<Card> cardOptional;

        // note: its is a little a mystery for me ATM why one should expect that method here to throw that exception,
        // but: oh well.
        try {
            cardOptional = this.cardRepository.findById(id);
        } catch (ResourceNotFoundException ignored) {
            cardOptional = Optional.empty();
        }

        // only admins gets "not found" exception because otherwise
        // possible that info about non-existence information leak already. o_o
        if(cardOptional.isEmpty() ){
            if (requesterIsAdmin) {
                throw new ResourceNotFoundException();
            } else {
                throw new PermissionDeniedException();
            }
        }

        // ressource was found.
        if (requesterIsAdmin) {
            return cardOptional.get();
        } else {
            boolean requesterIsOwner = Objects.equals(cardOptional.get().getBox().getOwner().getId(), requester.getId());
            // box of card either public or belonging to requester => read allowed
            if (requesterIsOwner || cardOptional.get().getBox().getPublic()) {
                return cardOptional.get();
            } else {
                throw new PermissionDeniedException();
            }
        }

    }

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

    // ML2: basics tested
    public Card update(Long id, CardDto cardDto, UserPrincipal requester) {
        // read card checks most permission conditions ...
        Card cardAsExists = this.read(id,requester);

        // ... but a problem remains: read is allowed for public boxes, but that does not allow updating for all.
        var updateDenied = !requester.isStudyBuddyAdmin()
                && !cardAsExists.getBox().getOwner().getId().equals(requester.getId());
        if(updateDenied){
            throw new PermissionDeniedException();
        }

        // card only has three properties one is allowed to change by endpoint, actually.
        if (cardDto.getQuestion() != null) cardAsExists.setQuestion(cardDto.getQuestion());
        if (cardDto.getAnswer() != null) cardAsExists.setAnswer(cardDto.getAnswer());
        // todo: media not yet in dto.
        // cardAsExists.setMedia(cardDto.getMedia());

        return cardRepository.save(cardAsExists);
    }

    //ML2: tested owner works
    public CardDto delete(Long id) throws ResourceNotFoundException{
        Card card = this.cardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        // authorization: only for parent box owner = self
        if(!isBoxOwnerPrincipalOrAdmin(card)){
            throw new PermissionDeniedException();
        }
        CardDto cardDto = new CardDto(card);
        this.cardRepository.deleteById(id);
        return cardDto;
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
