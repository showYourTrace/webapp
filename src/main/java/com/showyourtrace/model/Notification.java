package com.showyourtrace.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="NOTIFICATION")
public class Notification {

    @Id
    @GenericGenerator(name="notification_generator",strategy="increment")
    @GeneratedValue(generator="notification_generator")
    @Column(name = "NOTIFICATION_ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "BODY")
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
