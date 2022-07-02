package project.games.connectfour.view;

import project.games.connectfour.board.ConnectFourSpaceState.*;
import project.games.connectfour.board.ConnectFourGrid;

public class ConnectFourOutputCentre {
    public String generateBoardAscii(ConnectFourGrid grid) {
        String[] boardData = new String[42];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                switch (grid.board[i][j].getState()) {
                    case EMPTY -> boardData[(i * 7) + j] = " ";
                    case PLAYER_1_TOKEN -> boardData[(i * 7) + j] = "X";
                    case PLAYER_2_TOKEN -> boardData[(i * 7) + j] = "O";
                }
            }
        }
        return formatAsciiOutput(boardData);
    }

    public static String formatAsciiOutput(String[] boardData) {
        StringBuilder output = new StringBuilder("| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
        output.append("-----------------------------------------\n");
        for (int i = 0; i < 7; i++) {
            output.append(i);
            output.append("| ");
            for (int j = 0; j < 6; j++) {
                output.append(boardData[i + (j * 6)]);
                output.append(" | ");
            }
            output.delete(output.length() - 3, output.length());
            output.append(" |\n");

            output.append("-----------------------------------------\n");
        }

        return output.toString();
    }
}
