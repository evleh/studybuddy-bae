package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CardDto {
    private Long id;
    private Long boxId;

    @NotBlank
    @Size(min = 5, max = 2000) // to match the db-varchar length
    private String question;

    @NotBlank
    @Size(min = 5, max = 2000) // to match the db-varchar length
    private String answer;

    CardDto() {
        super();
    }

    public CardDto(Card card) {
        this();
        if (card != null) {
            this.id = card.getId();
            this.question = card.getQuestion();
            this.answer = card.getAnswer();
            if (card.getBox() != null) {
                this.boxId = card.getBox().getId();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
