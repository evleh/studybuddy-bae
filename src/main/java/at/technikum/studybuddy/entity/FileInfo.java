package at.technikum.studybuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.Instant;

@Entity
public class FileInfo {
    @Id
    private String fileName;

    @NotBlank
    private String originalFilename;

    @NotBlank
    private String contentType;

    @NotBlank
    private String createdBy;

    @CreationTimestamp
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public FileInfo() {}

    public FileInfo(String fileName, String originalFilename, String contentType, String createdBy, Instant createdAt, Instant updatedAt) {
        this.fileName = fileName;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
