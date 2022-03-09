package ru.itmo.wp.model.domain;

import java.util.Date;

public class Article extends AbstractArticle {
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }
}
