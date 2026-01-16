package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;

public class PublicBoxInfoDto {
    private String title;
    private String description;

    // TODO: when it exists perhaps the title image; requires File handling stuff I think

    public PublicBoxInfoDto(Box box) {
        this.title = box.getTitle();
        this.description = box.getDescription();
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



}
