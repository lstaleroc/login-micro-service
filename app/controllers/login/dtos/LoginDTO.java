package controllers.login.dtos;

/**
 * Login DTO that encapsulate login data from the request.
 * Created by Luis Talero on 12 Jun 2020
 */
public class LoginDTO {

    protected String email;

    protected String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


