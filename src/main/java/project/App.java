package project;

import project.games.battleships.board.Coords;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.exceptions.InvalidShipLocation;
import project.games.battleships.ships.Ship;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        PlayerBoard board = new PlayerBoard();

        try {
            board.ships.add(new Ship(new Coords("a1"), new Coords("a5")));
        } catch (InvalidShipLocation e) {
            e.printStackTrace();
        }

        board.shoot(new Coords("a3"));
        System.out.println(board.getBoardAsciiForEnemy());
    }
}
