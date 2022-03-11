package project.games.battleships.board;

import project.games.battleships.exceptions.ShipOverlappingException;
import project.games.battleships.ships.Ship;

import java.util.*;

public class PlayerBoard {
    public Coords[][] playerBoard = new Coords[10][10];
    public ArrayList<Ship> ships = new ArrayList<>();

    public PlayerBoard() {
        generateBlankBoard();
    }

    public String getBoardAsciiForEnemy() {
        String[] boardData = new String[100];
        //int i = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerBoard[i][j].getShot()) {
                    boardData[(i * 10) + j] = "x";
                    for (Ship ship : ships) {
                        if (ship.isOnCoordinate(playerBoard[i][j])) {
                            boardData[(i * 10) + j] = "✔";
                        }
                    }
                }
                else {
                    boardData[(i * 10) + j] = " ";
                }
            }
        }
        return formatAsciiOutput(boardData);
    }

    public String getBoardAsciiForPlayer() {
        String[] boardData = new String[100];
        //int i = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerBoard[i][j].getShot()) {
                    boardData[(i * 10) + j] = "x";
                    for (Ship ship : ships) {
                        if (ship.isOnCoordinate(playerBoard[i][j])) {
                            boardData[(i * 10) + j] = "✔";
                        }
                    }
                }
                else {
                    boardData[(i * 10) + j] = " ";
                    for (Ship ship : ships) {
                        if (ship.isOnCoordinate(playerBoard[i][j])) {
                            boardData[(i * 10) + j] = "o";
                        }
                    }

                }
            }
        }
        return formatAsciiOutput(boardData);
    }

    public static String formatAsciiOutput(String[] boardData) {
        StringBuilder output = new StringBuilder("  | A | B | C | D | E | F | G | H | I | J |\n");
        output.append("  -----------------------------------------\n");
        for (int i = 0; i < 10; i++) {
            output.append(i);
            output.append(" | ");
            for (int j = 0; j < 10; j++) {
                output.append(boardData[i + (j * 10)]);
                output.append(" | ");
            }
            output.delete(output.length() - 3, output.length());
            output.append(" |\n");

            output.append("  -----------------------------------------\n");
        }

        return output.toString();
    }

    private void generateBlankBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                playerBoard[i][j] = new Coords(CharacterAxis.NUMERICAL_ALPHABET.get(i+1), j);
            }
        }
    }

    public Boolean shoot(Coords coords) throws IndexOutOfBoundsException {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(playerBoard[i][j].is(coords)) {
                    playerBoard[i][j].setShot(true);
                    for (Ship ship : ships) {
                        if (ship.isOnCoordinate(playerBoard[i][j])) {
                            ship.getShot(playerBoard[i][j]);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addShip(Ship ship) throws ShipOverlappingException {
        for (Ship existingShips : ships) {
            for (Coords coordinate : ship.getCoordinates()) {
                if(existingShips.isOnCoordinate(coordinate)) {
                    throw new ShipOverlappingException("Ship is overlapping on point: " + coordinate);
                }
            }
        }

        //If it gets to this point it should be fine. May need to add more exceptions to limit ship sizes.
        ships.add(ship);
    }
}