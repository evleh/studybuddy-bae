package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BoxDto {

    private Long id;
    @NotBlank
    @Size(min = 5, max = 200)
    private String title;
    @Size(min = 0, max = 200)
    private String description;

    private Boolean isPublic;

    // missing here too: comments, cards, all connections ofc.
    private List<Long> commentIds ;

    public BoxDto() {
        // empty
    }

    public BoxDto(Box box) {
        this.id = box.getId();
        this.title = box.getTitle();
        this.description = box.getDescription();
        this.isPublic = box.getPublic();
        if (box.getComments() != null) {
            this.commentIds = box.getComments().stream().map(BoxComment::getId).toList();
        }
    }

    public Box makeAndGetBoxForCreation() { // the name "getBoxFor..." would confuse the framework.
        return new Box(
                this.title,
                this.description,
                this.isPublic
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }
}
