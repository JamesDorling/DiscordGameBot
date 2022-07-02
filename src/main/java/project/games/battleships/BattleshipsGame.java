package project.games.battleships;

import net.dv8tion.jda.api.entities.User;
import project.games.battleships.board.BoardManager;

public class BattleshipsGame {
    private BoardManager boards;

    public BoardManager getBoards() {
        return boards;
    }

    private boolean gameRunning = false;

    public void run(User player_one, User player_two) {
        boards = new BoardManager();
        boards.getPlayer1Board().setPlayer(player_one);
        boards.getPlayer2Board().setPlayer(player_two);
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
