package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.CardProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardProgressRepository extends JpaRepository<CardProgress, Long> {
}
