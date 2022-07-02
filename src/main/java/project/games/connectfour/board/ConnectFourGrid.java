package project.games.connectfour.board;


import net.dv8tion.jda.api.entities.User;
import project.games.connectfour.exceptions.ColumnFullException;
import project.games.connectfour.exceptions.InvalidColumnException;

//Now you might think "ooh James, make an interface and make both the battleships board and this use the same type of board"
//To that I would say I do not have the time to refactor all of battleship's code to fit an interface. Maybe one day.
public class ConnectFourGrid {
    public ConnectFourTokenSpace[][] board = new ConnectFourTokenSpace[7][6];

    public void addToken(int column, User player) throws ColumnFullException, InvalidColumnException {
        if (column < 0 || column > 7) throw new InvalidColumnException("Attempted to add token to a non-existent column!");
        for (int i = 6; i >= 0; i--) {
            if (board[column][i].getState() == ConnectFourSpaceState.EMPTY) {
                board[column][i].setState(ConnectFourSpaceState.PLAYER_1_TOKEN);
                return;
            }
        }
        throw new ColumnFullException("Tried to put a token into a full column!");
    }
}
