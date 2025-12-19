package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
}