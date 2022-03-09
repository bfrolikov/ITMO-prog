package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.ArticleView;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @noinspection unused
 */
public class IndexPage extends Page {
    ArticleService articleService = new ArticleService();
    UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {

    }

    @Json
    private void findAllVisibleOrderByCreationTime(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllVisibleOrderByCreationTime()
                .stream()
                .map(it -> new ArticleView(it, userService.find(it.getUserId()).getLogin()))
                .collect(Collectors.toList()));
    }
}
