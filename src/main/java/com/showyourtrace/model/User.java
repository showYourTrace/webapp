package com.showyourtrace.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="USER_OBJECT")
public class User extends AbstractLoggableEntity {

    @Id
    @GenericGenerator(name="user_generator",strategy="increment")
    @GeneratedValue(generator="user_generator")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "LOGIN", nullable = false, length = 255)
    private String login;

    @Column(name = "PWD", nullable = false, length = 512)
    private String pwd;

    @Column(name = "EMAIL", nullable = false, length = 254)
    private String email;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "SECOND_NAME", length = 255)
    private String secondName;

    @Column(name = "IS_ADMIN", nullable = false)
    private Boolean isAdmin = false;

    @Column(name = "RECEIVE_PROMO")
    private Boolean receivePromo = false;

    @Column(name = "CONFIRM_ID", length = 255)
    private String confirmId;

    @Column(name = "CONFIRM_ID_CREATED_AT")
    private Date confirmIdCreated;

    @Column(name = "REGISTERED_USER", nullable = false)
    private Boolean registeredUser;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
        return isAdmin;
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

    public Date getConfirmIdCreated() {
        return confirmIdCreated;
    }

    public void setConfirmIdCreated(Date confirmIdCreated) {
        this.confirmIdCreated = confirmIdCreated;
    }

    public Boolean getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(Boolean registeredUser) {
        this.registeredUser = registeredUser;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
}
