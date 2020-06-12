package models.user;

import models.profile.Profile;

import javax.persistence.*;

/**
 * Entity that represent an user
 * Created by Luis Talero on 12 Jun 2020
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByLoginAndCredential",
                query = "SELECT user FROM User user WHERE (user.email = :email AND user.password =:credential)")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToOne
    private Profile profile;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////GETTERS AND SETTERS//////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
