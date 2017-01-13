package com.languagelearn.object.response;

import java.util.Date;

public class SimpleDateResponse {

    private Date value;

    public SimpleDateResponse(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
