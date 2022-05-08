package project.games.battleships.view;

import project.games.battleships.board.Coords;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.exceptions.InvalidShipLength;
import project.games.battleships.exceptions.InvalidShipLocation;
import project.games.battleships.exceptions.ShipOverlappingException;
import project.games.battleships.ships.Ship;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputCentre {
    public static Coords receiveShot() {
        System.out.println("Where are you shooting: ");
        Scanner scanner = new Scanner(System.in);
        try {
            return Coords.of(scanner.nextLine());
        } catch (InputMismatchException e) {
            System.out.println("Invalid coordinates!");
            return receiveShot();
        }
    }

    public static void receiveShipLocation(int size, String shipName, PlayerBoard playerBoard) {
        System.out.println(OutputCentre.printCurrentPlayerGrid(playerBoard));
        System.out.println("Input coordinates for " + shipName + " with a length of " + size);
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Where does the ship start: ");
            Coords start = Coords.of(scanner.nextLine());
            System.out.println("Where does the ship end: ");
            Coords end = Coords.of(scanner.nextLine());
            playerBoard.addShip(new Ship(size, start, end));
        } catch (InputMismatchException e) {
            System.out.println("Invalid coordinates!");
            receiveShipLocation(size, shipName, playerBoard);
        } catch (InvalidShipLocation invalidShipLocation) {
            System.out.println("Invalid Ship Location!");
            receiveShipLocation(size, shipName, playerBoard);
        } catch (InvalidShipLength invalidShipLength) {
            System.out.println("Invalid Ship Length!");
            receiveShipLocation(size, shipName, playerBoard);
        } catch (ShipOverlappingException e) {
            System.out.println("Ship is overlapping another!");
            receiveShipLocation(size, shipName, playerBoard);
        }
    }
}
