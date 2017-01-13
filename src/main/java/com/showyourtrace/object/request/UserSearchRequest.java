package com.showyourtrace.object.request;

import com.showyourtrace.object.PageableRequest;

import java.util.Date;

public class UserSearchRequest extends PageableRequest {

    private Long id;
    private String login;
    private String name;
    private String secondName;
    private String email;
    private Date registeredBefore;
    private Date registeredAfter;
    private Boolean receivePromoOnly;

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

    public Date getRegisteredBefore() {
        return registeredBefore;
    }

    public void setRegisteredBefore(Date registeredBefore) {
        this.registeredBefore = registeredBefore;
    }

    public Date getRegisteredAfter() {
        return registeredAfter;
    }

    public void setRegisteredAfter(Date registeredAfter) {
        this.registeredAfter = registeredAfter;
    }

    public Boolean isReceivePromoOnly() {
        return receivePromoOnly;
    }

    public void setReceivePromoOnly(Boolean receivePromoOnly) {
        this.receivePromoOnly = receivePromoOnly;
    }
}
