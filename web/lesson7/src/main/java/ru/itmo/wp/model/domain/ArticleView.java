package ru.itmo.wp.model.domain;

import java.util.Date;

public class ArticleView extends AbstractArticle {
    private String userLogin;

    public ArticleView(Article article, String userLogin) {
        super(article.getId(), article.getTitle(), article.getText(), article.getCreationTime());
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(final String userLogin) {
        this.userLogin = userLogin;
    }
}
