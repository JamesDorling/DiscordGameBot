package project.games.battleships;

public class GameManager {
    private static BattleshipsGame battleshipsGame;

    public static void resetBattleships() {
        System.out.println("Resetting battleships game!");
        battleshipsGame = new BattleshipsGame();
        battleshipsGame.resetBoards();
    }


    public static BattleshipsGame getBattleshipsGame() {
        return battleshipsGame;
    }
}
