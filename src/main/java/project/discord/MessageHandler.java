package project.discord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import project.games.GameManager;
import project.games.battleships.BattleshipsGame;
import project.games.battleships.board.Coords;
import project.games.battleships.board.PlayerBoard;
import project.games.battleships.exceptions.InvalidPlayerException;
import project.games.battleships.exceptions.InvalidShipLength;
import project.games.battleships.exceptions.InvalidShipLocation;
import project.games.battleships.exceptions.ShipOverlappingException;
import project.games.battleships.ships.Ship;
import project.games.battleships.view.OutputCentre;
import project.games.connectfour.board.C4User;
import project.games.connectfour.exceptions.ColumnFullException;
import project.games.connectfour.exceptions.InvalidColumnException;
import project.games.connectfour.view.ConnectFourOutputCentre;

import java.util.ArrayList;
import java.util.Objects;

public class MessageHandler extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        switch(event.getName()) {
            case "ping" -> pingEvent(event);
            case "battleshipchallenge" -> challengeEvent(event);
            case "shoot" -> shootEvent(event);
            case "setup" -> setupEvent(event);
            case "setuptest" -> setupEventTest(event);
            case "connectfourchallenge" -> connectFourChallenge(event);
            case "addtoken" -> addTokenEvent(event);
            case "testtoken" -> testTokenEvent(event);
        }

    }

    public void pingEvent(SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                ).queue(); // Queue both reply and edit
    }

    public void challengeEvent(SlashCommandInteractionEvent event) {
        if(GameManager.getBattleshipsGame().isGameRunning()) {
            event.reply("Game currently running. Please wait for current game to end!").queue();
            System.err.println("User tried to start a game while game was already running.");
            return;
        }
        User player1, player2;
        GameManager.getBattleshipsGame().initialChannel = event.getTextChannel();
        try {
            System.out.println("Challenge command received!");
            player1 = event.getUser();
            player2 = Objects.requireNonNull(event.getOption("opponent")).getAsUser();
            System.out.println("Player has challenged: " + player2.getName());

            event.reply("Starting game! \nPlayers are: " + player1.getName() + " versus " + player2.getName()).queue();
            sendDirectMessage(player1, OutputCentre.printCurrentPlayerGrid(new PlayerBoard()));
            sendDirectMessage(player1, "Use /setup to place your ships!");
            sendDirectMessage(player2, OutputCentre.printCurrentPlayerGrid(new PlayerBoard()));
            sendDirectMessage(player2, "Use /setup to place your ships!");
            GameManager.getBattleshipsGame().run(player1, player2);
        }
        catch (NullPointerException e) {
            event.reply("No opponent found under that ID!").queue();
            System.err.println("Failed to find user. Name provided:" + event.getOption("opponent"));
            e.printStackTrace();
        }
    }

    public void shootEvent(SlashCommandInteractionEvent event) {
        if (!(event.getChannelType() == ChannelType.PRIVATE)) {
            event.reply("This command is DM only!").queue();
            return;
        }
        try {
            PlayerBoard board = GameManager.getBattleshipsGame().getBoards().checkPlayer(event.getUser());
            if(board.getTurn()) {
                System.out.println(event.getUser().getName() + " shot coordinates: " + Objects.requireNonNull(event.getOption("coords")).getAsString());
                if(board.getOpposingPlayer().shoot(Coords.of(Objects.requireNonNull(event.getOption("coords")).getAsString()))) {
                    event.reply("Hit!").queue();
                } else {
                    event.reply("Miss!").queue();
                }
                GameManager.getBattleshipsGame().getBoards().doTurn(board.getOpposingPlayer());
            }
            else {
                event.reply("Not your turn!").queue();
                System.err.println("Player tried to shoot when not his turn!");
            }
        } catch (InvalidPlayerException e) {
            System.err.println("Non-player tried shooting.");
            event.reply("You are not playing!").queue();
        } catch (NumberFormatException e) {
            System.err.println("Incorrect format for coordinates received.");
            event.reply("Those coordinates are incorrect. Please try again!").queue();
        }
    }

    private void setupEvent(SlashCommandInteractionEvent event) {
        if (!(event.getChannelType() == ChannelType.PRIVATE)) {
            event.reply("This command is DM only!").queue();
            return;
        }
        if(GameManager.getBattleshipsGame().isGameRunning()) {
            event.reply("Game is currently running. Cannot re-setup ships.").queue();
            System.err.println("Player tried setting up mid game");
            return;
        }
        try {
            event.reply("Setup Received!").queue();
            PlayerBoard board = GameManager.getBattleshipsGame().getBoards().checkPlayer(event.getUser()); //This doubles as a check for if a player called the command
            board.ships = new ArrayList<>();
            try {
                board.addShip(new Ship(5, Objects.requireNonNull(event.getOption("5lengthstart")).getAsString(),
                        Objects.requireNonNull(event.getOption("5lengthend")).getAsString()));
                board.addShip(new Ship(4, Objects.requireNonNull(event.getOption("4lengthstart")).getAsString(),
                        Objects.requireNonNull(event.getOption("4lengthend")).getAsString()));
                board.addShip(new Ship(3, Objects.requireNonNull(event.getOption("3lengthstart1")).getAsString(),
                        Objects.requireNonNull(event.getOption("3lengthend1")).getAsString()));
                board.addShip(new Ship(3, Objects.requireNonNull(event.getOption("3lengthstart2")).getAsString(),
                        Objects.requireNonNull(event.getOption("3lengthend2")).getAsString()));
                board.addShip(new Ship(2, Objects.requireNonNull(event.getOption("2lengthstart")).getAsString(),
                        Objects.requireNonNull(event.getOption("2lengthend")).getAsString()));
                sendDirectMessage(board.getPlayer(), "All set up! Ready to go!");
                sendDirectMessage(event.getUser(), OutputCentre.printCurrentPlayerGrid(board));
                if (board.getTurn()) {
                    sendDirectMessage(event.getUser(), OutputCentre.printCurrentEnemyGrid(board));
                    sendDirectMessage(board.getPlayer(), "Use /shoot to shoot an enemy space!");
                }
                return;
            } catch (NumberFormatException e) {
                System.err.println("Incorrect format for coordinates received.");
                sendDirectMessage(board, "Those coordinates are incorrect. Please try again!");
            } catch (InvalidShipLocation invalidShipLocation) {
                System.err.println("Ship placed in invalid location.");
                sendDirectMessage(board, "Ship placed in invalid location. Please try again!");
            } catch (InvalidShipLength invalidShipLength) {
                System.err.println("One ship was of invalid length.");
                sendDirectMessage(board, "Ship was wrong length! Please try again!");
            } catch (ShipOverlappingException e) {
                System.err.println("Two ships were overlapping.");
                sendDirectMessage(board, "Two of your ships are overlapping! Please try again!");
            }
            sendDirectMessage(board, "Something has gone wrong. Please try again!");
        } catch (InvalidPlayerException e) {
            System.err.println("Non-player tried setting up ships.");
            event.reply("You are not playing!").queue();
        }
    }

    private void setupEventTest(SlashCommandInteractionEvent event) {
        if (!(event.getChannelType() == ChannelType.PRIVATE)) {
            event.reply("This command is DM only!").queue();
            return;
        }
        if(GameManager.getBattleshipsGame().isGameRunning()) {
            event.reply("Game is currently running. Cannot re-setup ships.").queue();
            System.err.println("Player tried setting up mid game");
            return;
        }
        try {
            event.reply("Test setup Received!").queue();
            PlayerBoard board = GameManager.getBattleshipsGame().getBoards().checkPlayer(event.getUser()); //This doubles as a check for if a player called the command or not
            board.ships = new ArrayList<>(5);
            try {
                board.addShip(new Ship(2, "a0", "a1"));
                board.setTestRun();
                sendDirectMessage(board.getPlayer(), "All set up with test data! Ready to go!");
                sendDirectMessage(event.getUser(), OutputCentre.printCurrentPlayerGrid(board));
                sendDirectMessage(event.getUser(), OutputCentre.printCurrentEnemyGrid(board));
                if (board.getTurn()) {
                    sendDirectMessage(board.getPlayer(), "Use /shoot to shoot an enemy space!");
                }
                return;
            } catch (NumberFormatException e) {
                System.err.println("Incorrect format for coordinates received.");
                sendDirectMessage(board, "Those coordinates are incorrect. Please try again!");
            } catch (InvalidShipLocation invalidShipLocation) {
                System.err.println("Ship placed in invalid location.");
                sendDirectMessage(board, "Ship placed in invalid location. Please try again!");
            } catch (InvalidShipLength invalidShipLength) {
                System.err.println("One ship was of invalid length.");
                sendDirectMessage(board, "Ship was wrong length! Please try again!");
            } catch (ShipOverlappingException e) {
                System.err.println("Two ships were overlapping.");
                sendDirectMessage(board, "Two of your ships are overlapping! Please try again!");
            }
            sendDirectMessage(board, "Something has gone wrong. Please try again!");
        } catch (InvalidPlayerException e) {
            System.err.println("Non-player tried setting up ships.");
            e.printStackTrace();
            sendDirectMessage(event.getUser(), "You are not playing!");
        }
    }

    private void connectFourChallenge(SlashCommandInteractionEvent event) {
        if(GameManager.getConnectFourGame().isGameRunning()) {
            event.reply("Game currently running. Please wait for current game to end!").queue();
            System.err.println("User tried to start a game while game was already running.");
            return;
        }
        User player1, player2;
        GameManager.getConnectFourGame().initialChannel = event.getTextChannel();
        try {
            System.out.println("Connect four challenge command received!");
            player1 = event.getUser();
            player2 = Objects.requireNonNull(event.getOption("opponent")).getAsUser();
            System.out.println("Player one: " + player1.getName() + " - Player 2: " + player2.getName());

            event.reply("Starting game! \nPlayers are: " + player1.getName() + " versus " + player2.getName()).queue();
            //sendDirectMessage(player1, ConnectFourOutputCentre.getBoardDataForOutput());
            sendDirectMessage(player1, "Other player starts!");
            //sendDirectMessage(player2, ConnectFourOutputCentre.getBoardDataForOutput());
            sendDirectMessage(player2, "Use /addtoken to place a token!");
            GameManager.getConnectFourGame().run(player1, player2);


            try {
                C4User player = null;
                player = GameManager.getConnectFourGame().board.checkPlayer(event.getUser());
                System.out.println("Player one: " + player.getUser().getName() + " - Player 2: " + player.getOpponent().getUser().getName());
            } catch (InvalidPlayerException e) {
                e.printStackTrace();
            }
        }
        catch (NullPointerException e) {
            event.reply("No opponent found under that ID!").queue();
            System.err.println("Failed to find user. Name provided:" + event.getOption("opponent"));
            e.printStackTrace();
        }
    }

    private void addTokenEvent(SlashCommandInteractionEvent event) {
        if (!(event.getChannelType() == ChannelType.PRIVATE)) {
            event.reply("This command is DM only!").queue();
            return;
        }
        try {
            C4User player = GameManager.getConnectFourGame().board.checkPlayer(event.getUser()); //doubles
            event.reply("Token received!").queue();
            int[] tokenPlace = GameManager.getConnectFourGame().board.addToken(Objects.requireNonNull(event.getOption("column")).getAsInt(), player);
            if(GameManager.getConnectFourGame().board.checkForFour(tokenPlace[0], tokenPlace[1], player.getToken())) {
                sendDirectMessage(player.getUser(), "You win!");
                sendDirectMessage(player.getOpponent().getUser(), "You lose!");
                System.out.println("Player one: " + player.getUser().getName() + " - Player 2: " + player.getOpponent().getUser().getName());
                sendChannelMessage(GameManager.getConnectFourGame().initialChannel, player.getUser().getName() + " has won at Connect Four!");
                GameManager.resetConnectFour();
                return;
            }
            GameManager.getConnectFourGame().board.doTurn(player.getOpponent());
        } catch (ColumnFullException e) {
            sendDirectMessage(event.getUser(), "Column is full! Please try again.");
            System.err.println(event.getUser().getName() + " tried to add token to a full column.");
        } catch (InvalidColumnException e) {
            sendDirectMessage(event.getUser(), "Invalid column! Please refer to the grid.");
            System.err.println(event.getUser().getName() + " tried to add token to an invalid column.");
        } catch (InvalidPlayerException e) {
            event.reply("You arent playing.").queue();
            System.err.println(event.getUser().getName() + " tried to take part in someone else's game.");
        }
    }

    private void testTokenEvent(SlashCommandInteractionEvent event) {
        if (!(event.getChannelType() == ChannelType.PRIVATE)) {
            event.reply("This command is DM only!").queue();
            return;
        }
        try {
            C4User player = GameManager.getConnectFourGame().board.checkPlayer(event.getUser()); //doubles
            if (!player.isMyTurn()) {
                event.reply("Not your turn!").queue();
                return;
            }
            event.reply("Test Token received!").queue();
            for(int i = 0; i < 3; i++) {
                int[] tokenPlace = GameManager.getConnectFourGame().board.addToken(0, player);
                if (GameManager.getConnectFourGame().board.checkForFour(tokenPlace[0], tokenPlace[1], player.getToken())) {
                    sendDirectMessage(player.getUser(), "You win!");
                    sendDirectMessage(player.getOpponent().getUser(), "You lose!");
                    sendChannelMessage(GameManager.getConnectFourGame().initialChannel, player.getUser().getName() + " has won at Connect Four!");
                    GameManager.resetConnectFour();
                    return;
                }
            }
            GameManager.getConnectFourGame().board.doTurn(player.getOpponent());
        } catch (ColumnFullException e) {
            sendDirectMessage(event.getUser(), "Column is full! Please try again.");
            System.err.println(event.getUser().getName() + " tried to add token to a full column.");
        } catch (InvalidColumnException e) {
            sendDirectMessage(event.getUser(), "Invalid column! Please refer to the grid.");
            System.err.println(event.getUser().getName() + " tried to add token to an invalid column.");
        } catch (InvalidPlayerException e) {
            event.reply("You arent playing.").queue();
            System.err.println(event.getUser().getName() + " tried to take part in someone else's game.");
        }
    }


    public static void sendDirectMessage(User user, String message) {
        user.openPrivateChannel().flatMap((channel -> channel.sendMessage(message))).queue();
    }

    public static void sendDirectMessage(PlayerBoard player, String message) {
        player.getPlayer().openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue();
    }

    public static void sendChannelMessage(MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }
}
