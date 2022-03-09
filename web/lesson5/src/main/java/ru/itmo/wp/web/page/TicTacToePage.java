package ru.itmo.wp.web.page;

import ru.itmo.wp.util.tictactoe.State;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final int FIELD_SIZE = 3;

    private void action(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        State state = (State) session.getAttribute("state");
        if (state == null) {
            state = newState(session);
        }
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getKey().matches("cell_\\d\\d")) {
                String coords = entry.getKey().substring("cell_".length());
                state.makeMove(coords.charAt(0) - '0', coords.charAt(1) - '0');
                break;
            }
        }

        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        view.put("state", newState(request.getSession()));
    }

    private State newState(HttpSession session) {
        State state = new State(FIELD_SIZE);
        session.setAttribute("state", state);
        return state;
    }

}
