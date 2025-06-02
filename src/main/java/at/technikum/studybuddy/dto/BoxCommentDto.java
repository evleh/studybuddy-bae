package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.BoxComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BoxCommentDto {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 2000) // to match the db-varchar length
    private String text;

    // no user as of now
    // no box as of now
    // t.odo: displaydate als string


    BoxCommentDto() {
        super();
    }

    BoxCommentDto(BoxComment boxComment) {
        this();
        this.id = boxComment.getId();
        this.text = boxComment.getText();
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
}
