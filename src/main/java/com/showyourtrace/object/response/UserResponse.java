package com.showyourtrace.object.response;

public class UserResponse {

    private Long id;

	private String login;
    
	private String name;

	private String secondName;

	private String fullName;

	private String email;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
