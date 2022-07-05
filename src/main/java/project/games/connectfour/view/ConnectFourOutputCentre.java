package project.games.connectfour.view;

import project.games.GameManager;
import project.games.connectfour.board.ConnectFourGrid;

import java.util.Arrays;

public class ConnectFourOutputCentre {
//    public static String generateBoardAscii(ConnectFourGrid grid) {
//        String[] boardData = new String[42];
//        for (int i = 0; i < 6; i++) {
//            //System.out.println("i = " + i);
//            for (int j = 0; j < 7; j++) {
//                //System.out.println("j = " + j);
//                switch (grid.board[j][i].getState()) {
//                    case EMPTY -> boardData[i + (j * 6)] = " ";
//                    case PLAYER_1_TOKEN -> boardData[i + (j * 6)]= "X";
//                    case PLAYER_2_TOKEN -> boardData[i + (j * 6)] = "O";
//                }
//            }
//        }
//        return formatAsciiOutput(boardData);
//    }

    public static String generateBoardAscii(ConnectFourGrid grid) {
        String[] boardData = new String[42];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                switch (grid.board[i][j].getState()) {
                    case EMPTY -> boardData[(i * 6) + j] = " ";
                    case PLAYER_1_TOKEN -> boardData[(i * 6) + j]= "X";
                    case PLAYER_2_TOKEN -> boardData[(i * 6) + j] = "O";
                }
            }
        }
        System.out.println(Arrays.toString(boardData));
        return formatAsciiOutput(boardData);
    }

    /*public String getBoardAsciiForPlayer() {
        String[] boardData = new String[100];
        //int i = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerBoard[i][j].getShot()) {
                    boardData[(i * 10) + j] = "x";
                    for (Ship ship : ships) {
                        if (ship.isOnCoordinate(playerBoard[i][j])) {
                            boardData[(i * 10) + j] = "⨂";
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
     */

    private static String formatAsciiOutput(String[] boardData) {
        StringBuilder output = new StringBuilder("```\n| 0 | 1 | 2 | 3 | 4 | 5 | 6 |\n");
        output.append("-----------------------------\n");
        for (int i = 5; i >= 0; i--) {
            output.append("| ");
            for (int j = 0; j < 7; j++) {
                output.append(boardData[i + (j * 6)]);
                output.append(" | ");
            }
            output.delete(output.length() - 3, output.length());
            output.append(" |\n");

            output.append("-----------------------------\n");
        }
        output.append("```");
        return output.toString();
    }

    public static String getBoardDataForOutput() {
        return generateBoardAscii(GameManager.getConnectFourGame().board);
    }
}
