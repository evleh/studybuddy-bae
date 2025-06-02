package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Definiert Strategie wie PK generiert wird.
    private long id;
    // private long boxId;
    @NotBlank
    private String question;
    @NotBlank
    private String answer;

    private String media;

    @CreationTimestamp
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public Card() {
    }

    public Card(String question, String answer, String media, Instant createdAt, Instant updatedAt) {
        this.question = question;
        this.answer = answer;
        this.media = media;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant created) {
        this.createdAt = created;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant lastEdit) {
        this.updatedAt = lastEdit;
    }
}
