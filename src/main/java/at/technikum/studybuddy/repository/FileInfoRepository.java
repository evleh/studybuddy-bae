package at.technikum.studybuddy.repository;

import at.technikum.studybuddy.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    List<FileInfo> findByFileNameIn(List<String> fileNames);
}