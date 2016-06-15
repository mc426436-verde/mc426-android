package br.unicamp.ic.timeverde.dino.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private String firstName;
    private String lastName;
    @SerializedName("email")
    private String username;
    private String password;
    private List<String> authorities;
    private Token token;
    private Boolean isAdmin;
    private boolean activated;
    private Long id;
    private String langKey;
    private String login;
    private Date resetDate;
    private String resetKey;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!username.equals(user.getUsername())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
