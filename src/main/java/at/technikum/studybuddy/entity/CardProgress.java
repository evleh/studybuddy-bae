package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class CardProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // private long userId
    // private long cardId
    private int stage; // Possible stages: 1-4
    private Instant showAgain;
    private Instant lastSeen;

    public CardProgress() {
    }

    public CardProgress(long id, int stage, Instant showAgain, Instant lastSeen) {
        this.id = id;
        this.stage = stage;
        this.showAgain = showAgain;
        this.lastSeen = lastSeen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Instant getShowAgain() {
        return showAgain;
    }

    public void setShowAgain(Instant showAgain) {
        this.showAgain = showAgain;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }
}
