package project.games;

import project.games.battleships.BattleshipsGame;
import project.games.connectfour.ConnectFourGame;

public class GameManager {
    private static BattleshipsGame battleshipsGame;

    private static ConnectFourGame connectFourGame;

    public static void resetBattleships() {
        System.out.println("Resetting battleships game!");
        battleshipsGame = new BattleshipsGame();
        battleshipsGame.resetBoards();
    }

    public static void resetConnectFour() {
        System.out.println("Resetting connect four!");
        connectFourGame = new ConnectFourGame();
        connectFourGame.resetBoard();
    }


    public static BattleshipsGame getBattleshipsGame() {
        return battleshipsGame;
    }
}
