package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

public class UserDtoPrivilegedInfo extends UserDto {

    private boolean isAdmin;
    @Email
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private String country;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Long> boxIds;
    private List<Long> boxCommentIds;



    public UserDtoPrivilegedInfo() {}
    public UserDtoPrivilegedInfo(User user) {
        super(user);
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.country = user.getCountry();
        this.lastLogin = user.getLastLogin();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        if(user.getBoxes() != null){
            this.boxIds = user.getBoxes().stream().map(Box::getId).toList();
        }
        if(user.getBoxComments() != null){
            this.boxCommentIds = user.getBoxComments().stream().map(BoxComment::getId).toList();
        }
        this.isAdmin = user.isAdmin();
    }
    public UserDto downCastToAbstract() {
        /*
         * This returns the object only specifying the abstract class.
         *  This was introduced to help the list->stream->map->list transformation mechanism do its job.
         */
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
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

    public List<Long> getBoxIds() {
        return boxIds;
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
