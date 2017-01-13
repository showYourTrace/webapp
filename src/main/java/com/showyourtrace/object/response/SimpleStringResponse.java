package com.showyourtrace.object.response;

public class SimpleStringResponse {

    private String value;

    public SimpleStringResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
