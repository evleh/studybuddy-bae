package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.BoxComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxCommentRepository extends JpaRepository<BoxComment, Long> {
}
