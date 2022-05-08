package project.games.battleships.view;

import project.games.battleships.board.Coords;

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
}
