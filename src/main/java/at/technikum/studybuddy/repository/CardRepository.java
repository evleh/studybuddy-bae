package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
