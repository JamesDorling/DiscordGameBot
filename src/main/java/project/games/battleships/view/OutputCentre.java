package project.games.battleships.view;

import project.games.battleships.BattleshipsGame;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.ships.Ship;

public class OutputCentre {
    public static String printCurrentPlayerGrid(PlayerBoard player) {
        StringBuilder output = new StringBuilder("Player Board:");
        output.append("\n```");
        output.append(player.getBoardAsciiForPlayer());
        output.append("```");
        return output.toString();
    }

    public static String printCurrentEnemyGrid(PlayerBoard player) {
        StringBuilder output = new StringBuilder("Enemy Board:");
        output.append("\n```");
        output.append(player.getOpposingPlayer().getBoardAsciiForEnemy());
        output.append("```");
        return output.toString();
    }
}
