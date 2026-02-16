package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import org.springframework.stereotype.Service;

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

    public List<Card> readAll(UserPrincipal requester) {
        if (requester.isStudyBuddyAdmin()) {
            return this.cardRepository.findAll();
        } else {
            throw new PermissionDeniedException();
        }
    }

    public Card read(Long id, UserPrincipal requester) throws ResourceNotFoundException{

        boolean requesterIsAdmin = requester.isStudyBuddyAdmin();

        // need to know if the card exists even in the admin case, branch further down
        Optional<Card> cardOptional = this.cardRepository.findById(id);

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

    public Card create(CardDto cardDto, UserPrincipal requester){
        Long boxIdForCard = cardDto.getBoxId();
        if (boxIdForCard == null) throw new PermissionDeniedException(); // even admin should not make boxless cards

        try {
            Box boxForCard = this.boxService.read(boxIdForCard);

            if (boxForCard.getOwner().getId().equals(requester.getId()) || requester.isStudyBuddyAdmin()) {
                // do the thing
                Card newCard = new Card();
                newCard.setQuestion(cardDto.getQuestion());
                newCard.setAnswer(cardDto.getAnswer());
                // todo: add media when media implemented
                newCard.setBox(boxForCard);
                return this.cardRepository.save(newCard);
            } else {
                throw new PermissionDeniedException();
            }
        } catch(ResourceNotFoundException e) {
            if (requester.isStudyBuddyAdmin()) {
                throw new ResourceNotFoundException();
            } else {
                throw new PermissionDeniedException();
            }
        }
    }

    // ML2: basics tested
    public Card update(Long id, CardDto cardDto, UserPrincipal requester) {
        // read card checks most permission conditions ...
        Card cardAsExists = this.read(id,requester);

        // ... but a problem remains: read is allowed for public boxes, but that does not allow updating for all.
        boolean updateDenied = !requester.isStudyBuddyAdmin()
                && !cardAsExists.getBox().getOwner().getId().equals(requester.getId());
        if(updateDenied){
            throw new PermissionDeniedException();
        }

        // card only has three properties one is allowed to change by endpoint, actually.
        if (cardDto.getQuestion() != null) cardAsExists.setQuestion(cardDto.getQuestion());
        if (cardDto.getAnswer() != null) cardAsExists.setAnswer(cardDto.getAnswer());
        // todo: media not yet in dto.
        // cardAsExists.setMedia(cardDto.getMedia());

        // additional values in the dto put into the put request are ignored (for example: id, if it were set)

        return cardRepository.save(cardAsExists);
    }

    public CardDto delete(Long id, UserPrincipal requester) throws ResourceNotFoundException {
        Card card = this.read(id, requester);

        if (card.getBox().getOwner().getId().equals(requester.getId()) || requester.isStudyBuddyAdmin()) {
            CardDto cardDto = new CardDto(card); // create temp dto from card for return of deleted data
            this.cardRepository.deleteById(id);
            return cardDto;
        } else {
            throw new PermissionDeniedException();
        }
    }


}
