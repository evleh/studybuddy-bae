package at.technikum.studybuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.net.URL;
import java.time.Instant;

@Entity
@Table(name = "studybuddy_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isAdmin;
    private String username;
    private URL foto;
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

    // empty constructor
    public User() {

    }

    // full constructor
    public User(long id, boolean isAdmin, String username, URL foto, String email, String gender, String firstname, String lastname, String password, String country, Instant lastLogin, Instant created, Instant updatedAt) {
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

    // getters
    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
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

    public void setId(long id) {
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
}
