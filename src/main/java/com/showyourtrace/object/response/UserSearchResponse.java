package com.showyourtrace.object.response;

import java.util.Date;

public class UserSearchResponse {

    private Long id;
    private String login;
    private String name;
    private String secondName;
    private String email;
    private Date registrationDate;
    private Boolean receivePromo;
    private Boolean registeredUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isReceivePromo() {
        return receivePromo;
    }

    public void setReceivePromo(Boolean receivePromo) {
        this.receivePromo = receivePromo;
    }

    public Boolean getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(Boolean registeredUser) {
        this.registeredUser = registeredUser;
    }
}
