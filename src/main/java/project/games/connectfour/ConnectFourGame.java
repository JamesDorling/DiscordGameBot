package project.games.connectfour;

import net.dv8tion.jda.api.entities.User;
import project.games.connectfour.board.ConnectFourGrid;

public class ConnectFourGame {
    public ConnectFourGrid board;

    private boolean gameRunning = false;

    public void run(User playerOne, User playerTwo) {
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void resetBoard() {
        board = new ConnectFourGrid();
    }
}
