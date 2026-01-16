package at.technikum.studybuddy.repository;


import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box>findBoxesByIsPublicIsTrue();
    List<Box> findBoxesByOwner(User owner);
}
