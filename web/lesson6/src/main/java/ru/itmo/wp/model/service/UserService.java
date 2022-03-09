package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Event.Type;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @noinspection UnstableApiUsage
 */
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (user.getEmail().chars().filter(ch -> ch == '@').count() != 1) {
            throw new ValidationException("Invalid email format");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords do not match");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public int findCount() {
        return userRepository.findCount();
    }

    public User validateEnter(String loginOrEmail, String password) throws ValidationException {
        String passwordSha = getPasswordSha(password);
        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, passwordSha);
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, passwordSha);
        }
        if (user == null) {
            throw new ValidationException("Invalid login, email or password");
        }
        return user;
    }

    public void logEnter(User user) {
        saveEvent(user, Type.ENTER);
    }

    public void logLogout(User user) {
        saveEvent(user, Type.LOGOUT);
    }

    private void saveEvent(User user, Type type) {
        eventRepository.save(new Event(user.getId(), type));
    }

}
