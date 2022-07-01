package project.games.battleships.view;

import net.dv8tion.jda.api.entities.User;
import project.games.battleships.board.Coords;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.exceptions.InvalidShipLength;
import project.games.battleships.exceptions.InvalidShipLocation;
import project.games.battleships.exceptions.ShipOverlappingException;
import project.games.battleships.ships.Ship;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputCentre {
    public static Coords receiveShotCommandLine() {
        System.out.println("Where are you shooting: ");
        Scanner scanner = new Scanner(System.in);
        try {
            return Coords.of(scanner.nextLine());
        } catch (InputMismatchException e) {
            System.out.println("Invalid coordinates!");
            return receiveShotCommandLine();
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
            return;
        } catch (InputMismatchException e) {System.out.println("Invalid coordinates!");}
        catch (InvalidShipLocation invalidShipLocation) { System.out.println("Invalid Ship Location!"); }
        catch (InvalidShipLength invalidShipLength) { System.out.println("Invalid Ship Length!"); }
        catch (ShipOverlappingException e) { System.out.println("Ship is overlapping another!"); }
        catch (NumberFormatException e) { System.out.println("Invalid input."); }
        receiveShipLocation(size, shipName, playerBoard);

    }
}
