package project.games.connectfour.board;


import net.dv8tion.jda.api.entities.User;
import project.games.GameManager;
import project.games.battleships.exceptions.InvalidPlayerException;
import project.games.connectfour.exceptions.ColumnFullException;
import project.games.connectfour.exceptions.InvalidColumnException;
import project.games.connectfour.view.ConnectFourOutputCentre;

/*
    Now you might think "ooh James, make an interface and make both the battleships board and this use the same type of board"
    To that I would say I do not have the time to refactor all of battleship's code to fit an interface. Maybe one day.
    Also, they use different tiles to make the grid. There would be a way around this, but probably not one that would
    seem neat.
*/
public class ConnectFourGrid {
    public ConnectFourTokenSpace[][] board = new ConnectFourTokenSpace[7][6];

    private C4User player1, player2;

    public ConnectFourGrid() {
        generateBlankGrid();
        player1 = new C4User();
        player2 = new C4User();
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        player1.setToken(ConnectFourSpaceState.PLAYER_1_TOKEN);
        player2.setToken(ConnectFourSpaceState.PLAYER_2_TOKEN);
    }

    private void generateBlankGrid() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = new ConnectFourTokenSpace();
            }
        }
    }

    public int[] addToken(int column, C4User player) throws ColumnFullException, InvalidColumnException, InvalidPlayerException {
        if (column < 0 || column > 6) throw new InvalidColumnException("Attempted to add token to a non-existent column!");
        for (int i = 0; i < 6; i++) {
            if (board[column][i].getState() == ConnectFourSpaceState.EMPTY) {
                board[column][i].setState(player.getToken());
                System.out.println("Token placed at Row: " + i + " Column: " + column);
                return new int[]{i, column};
            }
        }
        throw new ColumnFullException("Tried to put a token into a full column!");
    }

    public void doTurn(C4User user) {
        GameManager.getConnectFourGame().setGameRunning(true);
        user.setTurn(true);
        user.getOpponent().setTurn(false);
        //Check if board is full
        if (checkBoardFull()) {
            user.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("Board full! Tie!")).queue();
            user.getOpponent().getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("Board full! Tie")).queue();
            GameManager.getConnectFourGame().initialChannel.sendMessage("Connect four ended in a tie!").queue();
            GameManager.resetConnectFour(); //Will also set game running to false
        }

        //Output boards
        user.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(ConnectFourOutputCentre.getBoardDataForOutput())).queue();
        user.getOpponent().getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(ConnectFourOutputCentre.getBoardDataForOutput())).queue();
        user.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("Use /addtoken to add a token!")).queue();
    }

    private boolean checkBoardFull() {
        for (ConnectFourTokenSpace[] connectFourTokenSpaces : board) {
            for (ConnectFourTokenSpace space : connectFourTokenSpaces) {
                if(space.getState() == ConnectFourSpaceState.EMPTY) return false;
            }
        }
        return true;
    }

    public C4User getPlayer1() {
        return player1;
    }

    public C4User getPlayer2() {
        return player2;
    }

    public C4User checkPlayer(User user) throws InvalidPlayerException {
        if (player1 == null || player2 == null) {
            System.err.println("A player was null");
            throw new InvalidPlayerException("A player was null");
        }
        if (user == player1.getUser()) {
            return player1;
        } else if (user == player2.getUser()) {
            return player2;
        }
        throw new InvalidPlayerException("Message received by user who is not playing!");
    }

    public boolean checkForFour(int row, int column, ConnectFourSpaceState state) {
        return (checkForDiagonalLineDownLeft(column, row, state) ||
                        checkForDiagonalLineDownRight(column, row, state) ||
                        checkForLineVertical(column, row, state) ||
                        checkForLineHorizontal(column, row, state));
    }

    private boolean checkForDiagonalLineDownLeft(int column, int row, ConnectFourSpaceState state) {
        System.out.println("Checking for diagonal down left victory");
        return (checkDirection(column, row, state, Directions.UPRIGHT, 0) + checkDirection(column, row, state, Directions.DOWNLEFT,0)) > 4;
    }

    private boolean checkForDiagonalLineDownRight(int column, int row, ConnectFourSpaceState state) {
        System.out.println("Checking for diagonal down right victory");
        return (checkDirection(column, row, state, Directions.UPLEFT, 0) + checkDirection(column, row, state, Directions.DOWNRIGHT,0)) > 4;
    }

    private boolean checkForLineVertical(int column, int row, ConnectFourSpaceState state) {
        System.out.println("Checking for vertical victory");
        return (checkDirection(column, row, state, Directions.UP, 0) + checkDirection(column, row, state, Directions.DOWN,0)) > 4;
    }

    private boolean checkForLineHorizontal(int column, int row, ConnectFourSpaceState state) {
        System.out.println("Checking for horizontal victory");
        return (checkDirection(column, row, state, Directions.LEFT, 0) + checkDirection(column, row, state, Directions.RIGHT,0)) > 4;
    }

    private int checkDirection(int column, int row, ConnectFourSpaceState state, Directions direction, int count) {
        try {
            if (board[column][row].getState() == state) count += 1;
            else return count;
        } catch (IndexOutOfBoundsException e) {
            return count;
        }
        if (count == 4) return 4;

        return switch (direction) {
            case RIGHT -> checkDirection(column + 1, row, state, direction, count);
            case UP -> checkDirection(column, row - 1, state, direction, count);
            case LEFT -> checkDirection(column - 1, row, state, direction, count);
            case DOWN -> checkDirection(column, row + 1, state, direction, count);
            case UPLEFT -> checkDirection(column + 1, row - 1, state, direction, count);
            case UPRIGHT -> checkDirection(column - 1, row - 1, state, direction, count);
            case DOWNLEFT -> checkDirection(column + 1, row + 1, state, direction, count);
            case DOWNRIGHT -> checkDirection(column - 1, row + 1, state, direction, count);
        };
    }

    enum Directions {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        UPLEFT,
        UPRIGHT,
        DOWNLEFT,
        DOWNRIGHT
    }
}
