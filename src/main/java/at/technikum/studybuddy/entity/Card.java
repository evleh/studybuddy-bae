package at.technikum.studybuddy.entity;

import java.time.Instant;

public class Card {
    private int id;
    // private int boxId;
    private String question;
    private String answer;
    private String media;
    private Instant created;
    private Instant lastEdit;

    public Card() {
    }

    public Card(int id, String question, String answer, String media, Instant created, Instant lastEdit) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.media = media;
        this.created = created;
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
