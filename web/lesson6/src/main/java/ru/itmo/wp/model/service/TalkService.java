package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.TalkView;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void saveTalk(Talk talk) {
        talkRepository.save(talk);
    }


    public Talk validateTalk(User sourceUser, String targetUserLogin, String text) throws ValidationException {
        if (text == null || text.isBlank()) {
            throw new ValidationException("Message can't be empty");
        }
        if (text.length() > 255) {
            throw new ValidationException("Message can't be longer than 255 characters");
        }
        User targetUser = userRepository.findByLogin(targetUserLogin);
        if (targetUser == null) {
            throw new ValidationException("Invalid target user");
        }
        return new Talk(sourceUser.getId(), targetUser.getId(), text);
    }

    public List<TalkView> getUserTalkViewsSortedByCreationTime(User user) {
        List<Talk> talks = talkRepository.findBySourceUserIdOrTargetUserId(user.getId());
        List<TalkView> talkViews = new ArrayList<>();
        for (Talk t : talks) {
            String sourceUserLogin = userRepository.find(t.getSourceUserId()).getLogin();
            String targetUserLogin = userRepository.find(t.getTargetUserId()).getLogin();
            talkViews.add(new TalkView(t.getId(), sourceUserLogin, targetUserLogin, t.getText(), t.getCreationTime()));
        }
        talkViews.sort(Comparator.comparing(TalkView::getCreationTime));
        return talkViews;
    }
}
