package com.languagelearn.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="MAILING_ATTACHMENT")
public class MailingAttachment {

    @Id
    @GenericGenerator(name="mailing_attachment_generator",strategy="increment")
    @GeneratedValue(generator="mailing_attachment_generator")
    @Column(name = "ATTACHMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILING_ID", nullable = false)
    private Mailing mailing;

    @Column(name = "FILE_URL", nullable = false)
    private String fileUrl;

    public MailingAttachment() {
    }

    public MailingAttachment(Mailing mailing, String fileUrl) {
        this.mailing = mailing;
        this.fileUrl = fileUrl;
    }

    public Mailing getMailing() {
        return mailing;
    }

    public void setMailing(Mailing mailing) {
        this.mailing = mailing;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
