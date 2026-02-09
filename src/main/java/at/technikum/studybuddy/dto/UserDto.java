package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.User;

import java.net.URL;

public abstract class UserDto {
    /*
     * Abstract class, defines a constructor signature
     * and attributes and methods every child class has in common.
     * If an abstract class has abstract methods, this forces the child to
     * implement this method.
     */
    private Long id;
    private String username;
    private String foto;


    UserDto() {}
    UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.foto = user.getFoto();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
