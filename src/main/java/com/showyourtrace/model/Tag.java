package com.showyourtrace.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TAG")
public class Tag {

    @Id
    @GenericGenerator(name="vendor_generator",strategy="increment")
    @GeneratedValue(generator="vendor_generator")
    @Column(name = "TAG_ID")
    private Long id;

    @Column(name = "TAG_NAME", length = 128, nullable = false)
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

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
}
