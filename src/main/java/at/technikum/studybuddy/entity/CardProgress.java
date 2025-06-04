package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
public class CardProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ToDo: private long userId
    // ToDo: private long cardId

    @NotBlank
    @Min(1)
    @Max(4)
    private int stage; // Possible stages: 1-4

    @CreationTimestamp
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    // Wieso keine Annotationen: Zeitpunkte abhängig von Logik gesetzt.
    // Daher sind bisher verwendete Annotationen für Zeit unpassend.
    private Instant showAgain;


    public CardProgress() {
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

    public Instant getShowAgain() {
        return showAgain;
    }

    public void setShowAgain(Instant showAgain) {
        this.showAgain = showAgain;
    }
}
