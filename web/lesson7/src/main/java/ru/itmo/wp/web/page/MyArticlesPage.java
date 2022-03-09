package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    ArticleService articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        validateAuthentication("Log in to view your articles");
        view.put("articles", articleService.findAllFilterByUserId(getUser().getId()));
    }

    @Json
    private void updateHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String id = request.getParameter("id");
        String hidden = request.getParameter("hidden");
        Article article = articleService.validateUpdateHidden(id, hidden, getUser());
        articleService.updateHidden(article);
        view.put("hidden", article.isHidden());
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {

    }
}
