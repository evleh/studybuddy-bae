package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.User;

import java.net.URL;
import java.util.List;

public abstract class UserDto {
    /*
     * Abstract class, defines a constructor signature
     * and attributes and methods every child class has in common.
     * If an abstract class has abstract methods, this forces the child to
     * implement this method.
     */
    private long id;
    private String username;
    private URL foto;
    private List<Long> boxIds;
    private List<Long> boxCommentIds;

    UserDto() {}
    UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.foto = user.getFoto();
        if(user.getBoxes() != null){
            this.boxIds = user.getBoxes().stream().map(Box::getId).toList();
        }
        if(user.getBoxComments() != null){
            this.boxCommentIds = user.getBoxComments().stream().map(BoxComment::getId).toList();
        }
    }

    public List<Long> getBoxIds() {
        return boxIds;
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

    public void setBoxIds(List<Long> boxIds) {
        this.boxIds = boxIds;
    }

    public List<Long> getBoxCommentIds() {
        return boxCommentIds;
    }

    public void setBoxCommentIds(List<Long> boxCommentIds) {
        this.boxCommentIds = boxCommentIds;
    }
}
