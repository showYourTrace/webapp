package com.languagelearn.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="MAILING")
public class Mailing {

    @Id
    @GenericGenerator(name="mailing_generator",strategy="increment")
    @GeneratedValue(generator="mailing_generator")
    @Column(name = "MAILING_ID")
    private Long id;

    @Column(name = "SUBJECT", length = 78)
    private String subject;

    @Column(name = "BODY", length = 2000)
    private String body;

    @Column(name = "CREATED_DATE", length = 50)
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @OneToMany(mappedBy = "mailing", fetch = FetchType.LAZY)
    private Set<MailingAttachment> mailAttachmentSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Set<MailingAttachment> getMailAttachmentSet() {
        return mailAttachmentSet;
    }

    public void setMailAttachmentSet(Set<MailingAttachment> mailAttachmentSet) {
        this.mailAttachmentSet = mailAttachmentSet;
    }
}
