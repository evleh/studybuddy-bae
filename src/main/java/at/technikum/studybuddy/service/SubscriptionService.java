package at.technikum.studybuddy.service;

import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.SubscriptionRepository;
import at.technikum.studybuddy.entity.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> readAll(){
        return this.subscriptionRepository.findAll();
    }

    public Subscription read(long id){
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
            if(subscription.isEmpty()){
                throw new ResourceNotFoundException();
            }
            return subscription.get();
    }

    public Subscription create(Subscription subscription) {
        return this.subscriptionRepository.save(subscription);
    }

    public Subscription update(long id, Subscription subscription){
        Optional<Subscription> findSubscription = subscriptionRepository.findById(id); // nur save wenn es schon existiert
        if(findSubscription.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.subscriptionRepository.save(subscription);
        return findSubscription.get();
    }

    public Subscription delete(long id) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        if(subscription.isEmpty()){
            throw new ResourceNotFoundException();
        }
        this.subscriptionRepository.deleteById(id);
        return subscription.get();
    }
}
