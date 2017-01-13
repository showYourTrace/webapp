package com.showyourtrace.object.request;

public class UserCreateRequest {

    private String login;
    private String pwd;
    private String name;
    private String secondName;
    private String email;
    private Boolean receivePromo;

    private String confirmId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public Boolean isReceivePromo() {
        return receivePromo;
    }

    public void setReceivePromo(Boolean receivePromo) {
        this.receivePromo = receivePromo;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

}
