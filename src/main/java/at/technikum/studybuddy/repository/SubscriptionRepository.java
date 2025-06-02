package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.Subscription;
import at.technikum.studybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
