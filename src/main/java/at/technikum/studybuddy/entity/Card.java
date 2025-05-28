package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Definiert Strategie wie PK generiert wird.
    private long id;
    // private long boxId;
    private String question;
    private String answer;
    private String media;

    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant lastEdit;

    public Card() {
    }

    public Card(long id, String question, String answer, String media, Instant created, Instant lastEdit) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.media = media;
        this.created = created;
        this.lastEdit = lastEdit;
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Instant lastEdit) {
        this.lastEdit = lastEdit;
    }
}
