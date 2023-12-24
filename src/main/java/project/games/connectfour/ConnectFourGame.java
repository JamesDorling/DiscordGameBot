package project.games.connectfour;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import project.games.connectfour.board.ConnectFourGrid;

public class ConnectFourGame {
    public ConnectFourGrid board;

    public TextChannel initialChannel;

    private boolean gameRunning = false;

    public void run(User playerOne, User playerTwo) {
        board = new ConnectFourGrid();
        board.getPlayer1().setUser(playerOne);
        board.getPlayer2().setUser(playerTwo);
        board.doTurn(board.getPlayer2());
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
