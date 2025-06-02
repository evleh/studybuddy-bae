package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.CardProgress;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardProgressService {
    private final CardProgressRepository cardProgressRepository;

    public CardProgressService(CardProgressRepository cardProgressRepository) {
        this.cardProgressRepository = cardProgressRepository;
    }

    public List<CardProgress> readAll(){
        return this.cardProgressRepository.findAll();
    }

    public CardProgress read(long id) throws ResourceNotFoundException {
        Optional<CardProgress> cardProgress = cardProgressRepository.findById(id);

        if(cardProgress.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return cardProgress.get();
    }

    public CardProgress create(CardProgress cardProgress){
        return this.cardProgressRepository.save(cardProgress);
    }

    public CardProgress update(long id, CardProgress cardProgress) throws ResourceNotFoundException{
        Optional<CardProgress> findCardProgress = cardProgressRepository.findById(id); // nur save wenn es schon existiert
        if(findCardProgress.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.cardProgressRepository.save(cardProgress);
        return findCardProgress.get();
    }

    public CardProgress delete(long id) throws ResourceNotFoundException{
        Optional<CardProgress> cardProgress = cardProgressRepository.findById(id);

        if(cardProgress.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.cardProgressRepository.deleteById(id);
        return cardProgress.get();
    }


}
