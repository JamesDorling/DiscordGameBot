package project.games.battleships.board;

import project.games.battleships.ships.Ship;

public class BoardManager {
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;

    public BoardManager() {
        player1Board = new PlayerBoard();
        player2Board = new PlayerBoard();
    }

    public PlayerBoard getPlayer1Board() {
        return player1Board;
    }

    public PlayerBoard getPlayer2Board() {
        return player2Board;
    }

    public Boolean checkGameNotOver() {
        return player1Board.checkStillAlive() && player2Board.checkStillAlive();
    }

    public boolean doTurn(PlayerBoard player) {
        return true;
    }
}
