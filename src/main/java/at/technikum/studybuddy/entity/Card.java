package at.technikum.studybuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Definiert Strategie wie PK generiert wird.
    private Long id;

    @NotBlank
    @Size(min = 1, max = 2000)
    private String question;
    @NotBlank
    @Size(min = 1, max = 2000)
    private String answer;

    private String media;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @CreationTimestamp
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public Card() {
    }

    public Card(String question, String answer, String media, Box box, Instant createdAt, Instant updatedAt) {
        this.question = question;
        this.answer = answer;
        this.media = media;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.box = box;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}
