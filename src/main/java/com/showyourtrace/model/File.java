package com.showyourtrace.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="FILE_OBJECT")
public class File extends AbstractLoggableEntity {

    @Id
    @GenericGenerator(name="file_object_generator",strategy="increment")
    @GeneratedValue(generator="file_object_generator")
    @Column(name = "FILE_OBJECT_ID")
    private Long id;

    @Column(name = "FILE_NAME", length = 255)
    private String name;

    @Column(name = "FILE_DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "FILE_EXTENSION", length = 50)
    private String extension;

    @Column(name = "FILE_SIZE")
    private Long size;

    @Column(name = "FILE_CONTENT")
    private byte[] content;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
