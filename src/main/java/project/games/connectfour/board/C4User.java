package project.games.connectfour.board;

import net.dv8tion.jda.api.entities.User;
// Its connect four you wally
public class C4User {
    private User user;
    private boolean turn = false;
    private C4User opponent;
    private ConnectFourSpaceState token;

    public User getUser() {
        return user;
    }

    public boolean isMyTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public C4User getOpponent() {
        return opponent;
    }

    public void setOpponent(C4User opponent) {
        this.opponent = opponent;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConnectFourSpaceState getToken() {
        return token;
    }

    public void setToken(ConnectFourSpaceState token) {
        this.token = token;
    }
}