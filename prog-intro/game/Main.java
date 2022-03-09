package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        InputParams gameData = new InputParams(3);
        int playerCount;
        while (true) {
            System.out.println("Enter n, k and the number of players");
            gameData.getInputParamsFromUser(in);
            playerCount = gameData.get(2);
            if (playerCount < 2 || playerCount > 4) {
                System.out.println("Unsupported number of players: " + playerCount);
            } else {
                break;
            }
        }
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            players.add(new HumanPlayer(System.out, in));
        }
        final Game game = new Game(false, players);
        System.out.println(game.play(new RhombicMNKBoard(gameData.get(0), gameData.get(1))));
        in.close();
    }
}
