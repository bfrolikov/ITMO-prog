package ru.itmo.wp.web.page;


import ru.itmo.wp.model.domain.TalkView;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    @Override
    protected void before(final HttpServletRequest request, final Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("Messages are available only for authenticated users");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
        putTalkViews(view);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        //No operations
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String targetUserLogin = request.getParameter("targetUserLogin");
        String text = request.getParameter("text");
        talkService.saveTalk(talkService.validateTalk(getUser(), targetUserLogin, text));
        throw new RedirectException("/talks");
    }

    private void putTalkViews(Map<String, Object> view) {
        view.put("talkViews", talkService.getUserTalkViewsSortedByCreationTime(getUser()));
    }

}
