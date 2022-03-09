package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        validateTextField("title", article.getTitle(), 26);
        validateTextField("text", article.getText(), 1500);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    private void validateTextField(String name, String value, int maxLen) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException(String.format("%s can't be empty", makeFirstLetterUppercase(name)));
        }
        if (value.length() > maxLen) {
            throw new ValidationException(String.format("%s can't be longer than %d characters", makeFirstLetterUppercase(name), maxLen));
        }
    }

    public Article validateUpdateHidden(String id, String hidden, User user) throws ValidationException {
        if (id == null || !id.matches("[0-9]+")) {
            throw new ValidationException("Invalid id");
        }
        if (hidden == null || (!hidden.equals("true") && !hidden.equals("false"))) {
            throw new ValidationException("Invalid hidden");
        }
        long articleId = Long.parseLong(id);
        Article article = articleRepository.find(articleId);
        if (article == null) {
            throw new ValidationException("No such article");
        }
        if (article.getUserId() != user.getId()) {
            throw new ValidationException("Only the author can change the visibility of an article");
        }
        article.setHidden(Boolean.parseBoolean(hidden));
        return article;
    }

    public List<Article> findAllVisibleOrderByCreationTime() {
        return articleRepository.findAllVisibleOrderByCreationTime();
    }

    public List<Article> findAllFilterByUserId(long userId) {
        return articleRepository.findAllFilterByUserId(userId);
    }

    public void updateHidden(Article article) {
        articleRepository.updateHidden(article);
    }


    private static String makeFirstLetterUppercase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
