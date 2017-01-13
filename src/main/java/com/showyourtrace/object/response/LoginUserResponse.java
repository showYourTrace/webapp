package com.showyourtrace.object.response;

public class LoginUserResponse {

	private String userName;

	private String email;
    
	private String phone;

    private Boolean hasTicket;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHasTicket() {
        return hasTicket;
    }

    public void setHasTicket(Boolean hasTicket) {
        this.hasTicket = hasTicket;
    }
}
