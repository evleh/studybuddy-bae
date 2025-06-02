package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // private User subscriber;
    // private Box box;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    //empty constructor
    public Subscription() {

    }

    //full constructor

    public Subscription(long id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
