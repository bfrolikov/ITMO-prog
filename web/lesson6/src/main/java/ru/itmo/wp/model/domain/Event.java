package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event {
    private long id;
    private long userId;
    private Type type;
    private Date creationTime;

    public Event(final long userId, final Type type) {
        this.userId = userId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public enum Type {
        ENTER, LOGOUT
    }
}
