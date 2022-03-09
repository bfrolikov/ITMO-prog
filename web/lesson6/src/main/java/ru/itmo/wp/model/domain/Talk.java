package ru.itmo.wp.model.domain;

import java.util.Date;

public class Talk {
    private long id;
    private long sourceUserId;
    private long targetUserId;
    private String text;
    private Date creationTime;

    public Talk() {
    }

    public Talk(long sourceUserId, long targetUserId, String text) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(final long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(final long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }
}
