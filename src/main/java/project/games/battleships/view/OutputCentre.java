package project.games.battleships.view;

import project.games.battleships.BattleshipsGame;
import project.games.battleships.ships.Ship;

public class OutputCentre {

    public static String printCurrentPlayer1GridForPlayer() {
        StringBuilder output = new StringBuilder("Your Board:");
        output.append("\n");
        output.append(BattleshipsGame.getBoards().getPlayer1Board().getBoardAsciiForPlayer());
        output.append("\n");
        return output.toString();
    }

    public static String printCurrentPlayer1GridForEnemy() {
        StringBuilder output = new StringBuilder("Enemy Board:");
        output.append("\n");
        output.append(BattleshipsGame.getBoards().getPlayer1Board().getBoardAsciiForEnemy());
        return output.toString();
    }

    public static String printCurrentPlayer2GridForPlayer() {
        StringBuilder output = new StringBuilder("Your Board:");
        output.append("\n");
        output.append(BattleshipsGame.getBoards().getPlayer2Board().getBoardAsciiForPlayer());
        output.append("\n");
        return output.toString();
    }

    public static String printCurrentPlayer2GridForEnemy() {
        StringBuilder output = new StringBuilder("Enemy Board:");
        output.append("\n");
        output.append(BattleshipsGame.getBoards().getPlayer2Board().getBoardAsciiForEnemy());
        return output.toString();
    }
}
