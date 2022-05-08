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
    public static void main(String[] args) { BattleshipsGame.run(); }
}
