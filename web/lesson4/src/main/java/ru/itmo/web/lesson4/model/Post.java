package ru.itmo.web.lesson4.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long userId;

    public Post(final long id, final String title, final String text, final long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUserId() {
        return userId;
    }
}
