package project.games.battleships.board;

import net.dv8tion.jda.api.entities.User;
import project.games.battleships.GameManager;
import project.games.battleships.exceptions.InvalidPlayerException;
import project.games.battleships.view.OutputCentre;

public class BoardManager {
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;

    public BoardManager() {
        player1Board = new PlayerBoard();
        player2Board = new PlayerBoard();
        player1Board.setOpposingPlayer(player2Board);
        player2Board.setOpposingPlayer(player1Board);
    }

    public PlayerBoard getPlayer1Board() {
        return player1Board;
    }

    public PlayerBoard getPlayer2Board() {
        return player2Board;
    }

    public Boolean checkGameNotOver() {
        return player1Board.checkStillAlive() && player2Board.checkStillAlive();
    }

    public void doTurn(PlayerBoard player) {
        player.setTurn(true);
        player.getOpposingPlayer().setTurn(false);
        if(!player.readyToPlay() || !player.getOpposingPlayer().readyToPlay()) {
            player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage("Someone isn't ready yet!")).queue();
            return;
        }
        GameManager.getBattleshipsGame().setGameRunning(true);
        if(!player.checkStillAlive()) {
            player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage("You Lose!")).queue();
            player.getOpposingPlayer().getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage("You Win!")).queue();
            GameManager.resetBattleships(); //Will also set game running to false
            return;
        }
        //Output boards
        player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage(OutputCentre.printCurrentPlayerGrid(player))).queue();
        player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage(OutputCentre.printCurrentEnemyGrid(player))).queue();
        player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage("Use the /shoot command to shoot!")).queue();
    }

    public PlayerBoard checkPlayer(User user) throws InvalidPlayerException {
        if (player1Board.getPlayer() == null || player2Board.getPlayer() == null) {
            System.err.println("A player was null");
            throw new InvalidPlayerException("A player was null");
        }
        if (user == player1Board.getPlayer()) {
            return player1Board;
        } else if (user == player2Board.getPlayer()) {
            return player2Board;
        }
        throw new InvalidPlayerException("Message received by user who is not playing!");
    }
}
