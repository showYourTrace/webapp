package com.languagelearn.object.request;

/**
 * Request with reference id=value pair
 */
public class ReferenceRequest {

    private Long id;
    private String value;
    private String name;

    public ReferenceRequest() {
    }

    public ReferenceRequest(Long id, String value) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}