package com.languagelearn.object.response;

public class ConfirmResultResponse {

    public boolean success;
    public String userMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }
}
