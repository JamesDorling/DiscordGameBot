package project.games.battleships.board;

import project.games.battleships.view.InputCentre;
import project.games.battleships.view.OutputCentre;

public class BoardManager {
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;

    public BoardManager() {
        player1Board = new PlayerBoard();
        player2Board = new PlayerBoard();
        player1Board.setOpposingPlayer(player2Board);
        player2Board.setOpposingPlayer(player1Board);
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

    public void doTurn(PlayerBoard player) {
        //Output boards
        System.out.println(OutputCentre.printCurrentPlayerGrid(player));
        System.out.println(OutputCentre.printCurrentEnemyGrid(player));

        //Receive input
        Coords shot = InputCentre.receiveShot();
        //Shoot at input
        if(player.getOpposingPlayer().shoot(shot) && checkGameNotOver()) //If a hit, and the game is not over, give the player another turn
        {
            doTurn(player);
        }
    }
}
