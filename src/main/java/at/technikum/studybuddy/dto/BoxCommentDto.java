package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.BoxComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class BoxCommentDto {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 2000) // to match the db-varchar length
    private String text;

    private Long boxId ;
    private Long authorId;

    private Instant createdAt;
    private Instant updatedAt;


    public BoxCommentDto() {

    }

    public BoxCommentDto(BoxComment boxComment) {
        if (boxComment != null) {
            this.id = boxComment.getId();
            this.text = boxComment.getText();
            if (boxComment.getBox() != null) {
                this.boxId = boxComment.getBox().getId();
            }
            if (boxComment.getAuthor() != null) {
                this.authorId = boxComment.getAuthor().getId();
            }
            this.createdAt = boxComment.getCreatedAt();
            this.updatedAt = boxComment.getUpdatedAt();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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
