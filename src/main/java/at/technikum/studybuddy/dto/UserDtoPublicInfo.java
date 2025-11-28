package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.User;

import java.net.URL;

public class UserDtoPublicInfo extends UserDto {

    private long id;
    private String username;
    private URL foto;

    public UserDtoPublicInfo() { };
    public UserDtoPublicInfo(User fromUserEntity) {
        this.id = fromUserEntity.getId();
        this.username = fromUserEntity.getUsername();
        this.foto = fromUserEntity.getFoto();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public URL getFoto() {
        return foto;
    }

    public void setFoto(URL foto) {
        this.foto = foto;
    }
}
