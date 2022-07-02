package project.games.connectfour.board;

public class ConnectFourTokenSpace {
    private ConnectFourSpaceState state = ConnectFourSpaceState.EMPTY;

    public ConnectFourSpaceState getState() {
        return state;
    }

    public void setState(ConnectFourSpaceState state) {
        this.state = state;
    }
}
