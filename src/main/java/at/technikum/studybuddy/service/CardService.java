package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.RoleTypes;
import jakarta.annotation.security.RolesAllowed;
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

    @RolesAllowed(RoleTypes.ADMIN)
    public List<Card> readAll(){
        return this.cardRepository.findAll();
    }

    public Card read(long id) throws ResourceNotFoundException{
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return card.get();
    }


    public Card create(CardDto cardDto){
        Card card = new Card();
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        card.setBox(boxService.readBoxById(cardDto.getBoxId()));
        return this.cardRepository.save(card);
    }

    public Card update(long id, CardDto cardDto){
        Optional<Card> cardRepo = this.cardRepository.findById(id);
        // Eventuell InputMismatchException wenn id-Paramater und card-id nicht übereinstimmen

        if(cardRepo.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Card card = read(id);
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        return cardRepository.save(card);
    }

    public CardDto delete(long id) throws ResourceNotFoundException{
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.cardRepository.deleteById(id);
        return new CardDto(card.get());

    }
}
