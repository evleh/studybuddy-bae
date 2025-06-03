package at.technikum.studybuddy.entity;

import at.technikum.studybuddy.dto.BoxDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    // aka "Kartei"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    // doLater: box belongs to a user
    // doLater: box has Cards
    // doLater: box has subscribers


    // comment: if I read https://www.baeldung.com/hibernate-one-to-many right
    // mappedBy specifies, as a string, the *name* of the property in the other Class that refers to here
    @OneToMany(mappedBy = "box")
    private List<BoxComment> comments ;


    @NotBlank
    @Size(min = 5, max = 200)
    private String title;
    @Column(length = 2048)
    private String description;

    private Boolean isPublic;

    public Box() {
    }

    public Box(String title, String description, Boolean isPublic) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
    }

    public Box updateFromBoxDto(BoxDto boxDto) {
        title = boxDto.getTitle()==null?title:boxDto.getTitle();
        description = boxDto.getDescription()==null?description:boxDto.getTitle();
        isPublic = boxDto.getPublic()==null?isPublic:boxDto.getPublic();
        return this;
    }



    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BoxComment> getComments() {
        return comments;
    }

    public void setComments(List<BoxComment> comments) {
        this.comments = comments;
    }
}
