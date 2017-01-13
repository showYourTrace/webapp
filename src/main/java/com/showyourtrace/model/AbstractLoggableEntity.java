package com.languagelearn.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractLoggableEntity {

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate = new Date();

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGED_DATE")
    private Date changedDate;

    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
