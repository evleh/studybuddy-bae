package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.exceptions.CardNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAll(){
        return this.cardRepository.findAll();
    }

    public Card get(long id){
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new CardNotFoundException();
        }
        return card.get();
    }


    public Card create(Card card){
        return this.cardRepository.save(card);
    }


    public Card update(long id, Card card){
        return null;
    }

    public Card delete(long id){
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new CardNotFoundException();
        }

        this.cardRepository.deleteById(id);
        return card.get();

    }
}
