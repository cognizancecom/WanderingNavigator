package com.example.wanderingnavigator.entities;

import java.util.Date;

public abstract class BaseEntity {
    protected Date createdDate;
    protected Date modifiedDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public BaseEntity() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }

    public abstract boolean validate();


}
