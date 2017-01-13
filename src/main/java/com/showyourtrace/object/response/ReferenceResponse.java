package com.showyourtrace.object.response;

/**
 * Response with reference id=value pair
 */
public class ReferenceResponse {

    private Long id;
    private String value;

    public ReferenceResponse(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
