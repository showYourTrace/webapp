package com.languagelearn.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="FILE_OBJECT")
public class FileObject {

    @Id
    @GenericGenerator(name="file_object_generator",strategy="increment")
    @GeneratedValue(generator="file_object_generator")
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "URL", length = 255)
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
