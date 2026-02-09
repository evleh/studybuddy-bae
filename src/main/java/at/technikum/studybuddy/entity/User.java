package at.technikum.studybuddy.entity;

import at.technikum.studybuddy.security.RoleTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.net.URL;
import java.time.Instant;
import java.util.List;

@Entity
@Table(
        name = "studybuddy_user",
        uniqueConstraints = @UniqueConstraint(columnNames={"username"})
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isAdmin;
    private String username;
    private String foto;
    @Email
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String password;
    @NotBlank
    private String country;
    private Instant lastLogin;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy = "owner")
    private List<Box> boxes;
    @OneToMany(mappedBy = "author")
    private List<BoxComment> boxComments;


    // empty constructor
    public User() {

    }

    // full constructor
    public User(Long id, boolean isAdmin, String username, URL foto, String email, String gender, String firstname, String lastname, String password, String country, Instant lastLogin, Instant created, Instant updatedAt) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.foto = foto;
        this.email = email;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.country = country;
        this.lastLogin = lastLogin;
        this.createdAt = created;
        this.updatedAt = updatedAt;
    }
    public String getRole() {
        if (this.isAdmin) {
            return RoleTypes.ADMIN;
        } else {
            return RoleTypes.REGISTERED;
        }
    }

    // getters
    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public URL getFoto() {
        return foto;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFoto(URL foto) {
        this.foto = foto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<BoxComment> getBoxComments() {
        return boxComments;
    }

    public void setBoxComments(List<BoxComment> boxComments) {
        this.boxComments = boxComments;
    }
}
