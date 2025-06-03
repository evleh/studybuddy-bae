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

    @OneToMany
    private List<BoxComment> boxComments ;


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

    public List<BoxComment> getBoxComments() {
        return boxComments;
    }

    public void setBoxComments(List<BoxComment> boxComments) {
        this.boxComments = boxComments;
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
}
