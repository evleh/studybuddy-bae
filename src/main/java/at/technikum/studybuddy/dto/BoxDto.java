package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.Card;
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

    // Entity connections. Currently, DTOs return only return ids. one todo is to make this more realistic.
    private Long ownerId;
    private List<Long> commentIds;
    private List<Long> cardIds;

    public BoxDto() {
        // empty
    }

    public BoxDto(Box box) {
        this.id = box.getId();
        this.title = box.getTitle();
        this.description = box.getDescription();
        this.isPublic = box.getPublic();
        this.ownerId = box.getOwner().getId();
        if (box.getComments() != null) {
            this.commentIds = box.getComments().stream().map(BoxComment::getId).toList();
        }
        if(box.getCards() != null){
            this.cardIds = box.getCards().stream().map(Card::getId).toList();
        }
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }

    public List<Long> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Long> cardIds) {
        this.cardIds = cardIds;
    }
}
