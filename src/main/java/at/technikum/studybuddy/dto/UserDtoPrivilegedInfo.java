package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class UserDtoPrivilegedInfo extends UserDtoPublicInfo {

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



    public UserDtoPrivilegedInfo() {}
    public UserDtoPrivilegedInfo(User fromUserEntity) {
        super(fromUserEntity);
        this.email = fromUserEntity.getEmail();
        this.gender = fromUserEntity.getGender();
        this.firstname = fromUserEntity.getFirstname();
        this.lastname = fromUserEntity.getLastname();
        this.country = fromUserEntity.getCountry();
        this.lastLogin = fromUserEntity.getLastLogin();
        this.createdAt = fromUserEntity.getCreatedAt();
        this.updatedAt = fromUserEntity.getUpdatedAt();
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

}
