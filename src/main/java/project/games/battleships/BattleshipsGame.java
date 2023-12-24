package project.games.battleships;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import project.games.battleships.board.BoardManager;

public class BattleshipsGame {
    private BoardManager boards;

    public TextChannel initialChannel;

    public BoardManager getBoards() {
        return boards;
    }

    private boolean gameRunning = false;

    public void run(User playerOne, User playerTwo) {
        boards = new BoardManager();
        boards.getPlayer1Board().setPlayer(playerOne);
        boards.getPlayer2Board().setPlayer(playerTwo);
        boards.doTurn(boards.getPlayer2Board());
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void resetBoards() {
        boards = new BoardManager();
    }
}
