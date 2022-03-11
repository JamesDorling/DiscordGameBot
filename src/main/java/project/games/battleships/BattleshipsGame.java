package project.games.battleships;

import project.games.battleships.board.BoardManager;

public class BattleshipsGame {
    private static BoardManager boards = new BoardManager();

    public static BoardManager getBoards() {
        return boards;
    }

    public static void run() {
        Boolean gameRunning = true;

        /* Setup game
        Get the players to input their ship locations on separate threads
        then put them in the loop to play the game
        */

        while (boards.checkGameNotOver()) {
            // Do a turn

        }
    }
}
