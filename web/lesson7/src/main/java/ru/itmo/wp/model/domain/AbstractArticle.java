package ru.itmo.wp.model.domain;

import java.util.Date;

public abstract class AbstractArticle {
    private long id;
    private String title;
    private String text;
    private boolean hidden;
    private Date creationTime;

    protected AbstractArticle(long id, String title, String text, Date creationTime) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime;
    }

    public AbstractArticle() {
    }


    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
}
