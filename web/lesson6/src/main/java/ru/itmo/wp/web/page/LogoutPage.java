package ru.itmo.wp.web.page;

import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    private final UserService service = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        service.logLogout(getUser());
        request.getSession().removeAttribute("user");
        setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
