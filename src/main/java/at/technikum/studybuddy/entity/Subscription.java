package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Subscription {
    @Id
    private int id;
    // private User subscriber;
    // private Box box;
    private Instant added;

    //empty constructor
    public Subscription() {

    }

    //full constructor

    public Subscription(int id, Instant added) {
        this.id = id;
        this.added = added;
    }
}
