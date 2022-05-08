package project;

import project.games.battleships.BattleshipsGame;
import project.games.battleships.board.Coords;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.exceptions.InvalidShipLength;
import project.games.battleships.exceptions.InvalidShipLocation;
import project.games.battleships.exceptions.ShipOverlappingException;
import project.games.battleships.ships.Ship;
import project.games.battleships.view.OutputCentre;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        PlayerBoard player1Board = BattleshipsGame.getBoards().getPlayer1Board();
        PlayerBoard player2Board = BattleshipsGame.getBoards().getPlayer2Board();

        try {
            player1Board.addShip(new Ship(5, Coords.of("a1"), Coords.of("a5")));
            player1Board.addShip(new Ship(5, Coords.of("b6"), Coords.of("j6")));
            player1Board.addShip(new Ship(5, Coords.of("b3"), Coords.of("e5"))); // Throws exception (Good)
            player2Board.addShip(new Ship(5, Coords.of("a1"), Coords.of("a6")));
        } catch (InvalidShipLocation | InvalidShipLength | ShipOverlappingException e) {
            e.printStackTrace();
        }

        BattleshipsGame.run();
    }
}
