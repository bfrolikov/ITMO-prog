package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public abstract class Page {
    private HttpSession session = null;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.session = request.getSession();
        putUser(view);
        putMessage(view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {

    }

    private void putUser(Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    private void putMessage(Map<String, Object> view) {
        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            session.removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        session.setAttribute("message", message);
    }

    protected String getMessage() {
        return (String) session.getAttribute("message");
    }

    protected void setUser(User user) {
        session.setAttribute("user", user);
    }

    protected User getUser() {
        return (User) session.getAttribute("user");
    }

    protected void validateAuthentication(String message) {
        if (getUser() == null) {
            setMessage(message);
            throw new RedirectException("/index");
        }
    }
}
