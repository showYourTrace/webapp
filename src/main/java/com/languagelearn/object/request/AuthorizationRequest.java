package com.languagelearn.object.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AuthorizationRequest {
    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    @Length(min=1, max=1024)
    private String pwd;


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
}
