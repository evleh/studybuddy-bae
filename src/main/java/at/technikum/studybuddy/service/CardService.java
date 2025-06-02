package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAll(){
        return this.cardRepository.findAll();
    }

    public Card get(long id) throws ResourceNotFoundException{
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return card.get();
    }


    public Card create(Card card){
        return this.cardRepository.save(card);
    }


    public Card update(long id, Card card){
        return this.cardRepository.save(card);
    }

    public Card delete(long id) throws ResourceNotFoundException{
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.cardRepository.deleteById(id);
        return card.get();

    }
}
