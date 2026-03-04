package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;

public class PublicBoxInfoDto {
    private String title;
    private String description;
    private Long id;


    public PublicBoxInfoDto(Box box) {
        this.title = box.getTitle();
        this.description = box.getDescription();
        this.id = box.getId();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
