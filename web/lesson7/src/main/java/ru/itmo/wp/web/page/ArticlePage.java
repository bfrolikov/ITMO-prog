package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    ArticleService articleService = new ArticleService();

    @Override
    protected void before(final HttpServletRequest request, final Map<String, Object> view) {
        super.before(request, view);
        validateAuthentication("Creating articles is only available for authenticated users");
    }


    private void action(HttpServletRequest request, Map<String, Object> view) {
        //No operations
    }

    @Json
    private void newArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setUserId(getUser().getId());
        articleService.validateArticle(article);
        articleService.save(article);
        setMessage("Article created");
        throw new RedirectException("/article");
    }

}
