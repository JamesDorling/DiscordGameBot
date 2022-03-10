package project.games.battleships.board;

import project.games.battleships.ships.Ship;

import java.util.*;

public class PlayerBoard {
    public Map<Coords, Boolean> board = new TreeMap<>();
    public Coords[][] playerBoard = new Coords[10][10];
    public ArrayList<Ship> ships = new ArrayList<>();

    public PlayerBoard() {
        generateBlankBoard();
    }

    public String getBoardAsciiForEnemy() {
        String[] boardData = new String[100];
        int i = 0;
        for (Map.Entry<Coords, Boolean> entry : board.entrySet()) {
            if (entry.getValue())
                for (Ship ship : ships) {
                    if (ship.isOnCoordinate(entry.getKey())) {
                        boardData[i] = "✔";
                    } else {
                        boardData[i] = "x";
                    }
                }

            else
                boardData[i] = " ";
            i++;
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
                output.append(boardData[i + j]);
                output.append(" | ");
            }
            output.delete(output.length() - 3, output.length());
            output.append(" |\n");

            output.append("  -----------------------------------------\n");
        }

        return output.toString();
    }

//    private void generateBlankBoard() {
//        for (int i = 1; i < 11; i++) {
//            for (int j = 0; j < 10; j++) {
//                board.put(new Coords(CharacterAxis.NUMERICAL_ALPHABET.get(i), j), false);
//            }
//        }
//
//        System.out.println(CharacterAxis.NUMERICAL_ALPHABET);
//        System.out.println(board);
//    }

    private void generateBlankBoard() {
        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 10; j++) {

            }
        }
    }

    public void shoot(Coords coords) throws IndexOutOfBoundsException {
        board.replace(coords, true);
    }
}