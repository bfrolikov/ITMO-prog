package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    Article find(long id);

    List<Article> findAllFilterByUserId(long userId);

    void updateHidden(Article article);

    List<Article> findAllVisibleOrderByCreationTime();
}
