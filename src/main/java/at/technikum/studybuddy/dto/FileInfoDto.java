package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.FileInfo;

import java.time.Instant;

public class FileInfoDto {

    private String fileName;
    private String ownerName;
    private Instant createdAt;
    private String originalFileName;
    private String contentType;

    public FileInfoDto() {
    }

    public FileInfoDto(FileInfo fileInfo) {
        this.fileName = fileInfo.getFileName();
        this.ownerName = fileInfo.getOwnerName();
        this.createdAt = fileInfo.getCreatedAt();
        this.originalFileName = fileInfo.getOriginalFilename();
        this.contentType = fileInfo.getContentType();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
