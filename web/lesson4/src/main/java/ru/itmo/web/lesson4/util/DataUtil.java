package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.RED),
            new User(6, "pashka", "Pavel Mavrin", User.Color.BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final List<Post> POSTS = List.of(
            new Post(1, "Eureka!", "I've always had a thing against Landon Donovan. The tag of \"best US " +
                    "soccer player of his generation\" rang a bit hollow when you looked at what he's achieved on a " +
                    "global scale. His two aborted attempts at Bayer Leverkusen in Germany really frustrated me.", 6),
            new Post(5, "Good Point", "Having a terrible morning all round today. Overworked and " +
                    "undervalued, I was sitting in the clinic and I locked myself in the room for half an hour to clear" +
                    " my thoughts before seeing any patients.", 11),
            new Post(7, "Screaming Eagles", "Happy birthday blog and whatnot. 4 years now since this all " +
                    "started, and this is my 101st post. Been a while since the last one but I guess work and life " +
                    "caught up with me and the interest in blogging had started to wane a bit.\n", 9)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
