package at.technikum.studybuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.net.URL;
import java.time.Instant;

@Entity
@Table(name = "studybuddy_user")
public class User {
    @Id
    private long id;
    private boolean isAdmin;
    private String username;
    private URL foto;
    private String email;
    private String gender;
    private String firstname;
    private String lastname;
    private String password;
    private String country;
    private Instant lastLogin;
    private Instant created;
    private Instant lastEdit;

    // empty constructor
    public User() {

    }

    // full constructor
    public User(long id, boolean isAdmin, String username, URL foto, String email, String gender, String firstname, String lastname, String password, String country, Instant lastLogin, Instant created, Instant lastEdit) {
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
        this.created = created;
        this.lastEdit = lastEdit;
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

    public Instant getCreated() {
        return created;
    }

    public Instant getLastEdit() {
        return lastEdit;
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

    public void setCreated(Instant created) {
        this.created = created;
    }

    public void setLastEdit(Instant lastEdit) {
        this.lastEdit = lastEdit;
    }
}
