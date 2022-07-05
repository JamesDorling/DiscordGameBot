package project.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import project.config.Config;
import project.games.GameManager;

import javax.security.auth.login.LoginException;

public class DiscordManager {

    public static void runBot() {
        try {
            JDA jda = JDABuilder.createDefault(Config.botToken())
                    .setActivity(Activity.watching("you play battleships"))
                    .build();

            CommandListUpdateAction commands = jda.updateCommands();

            commands.addCommands(
                    Commands.slash("ping", "Calculate the ping of the bot"),

                    Commands.slash("battleshipchallenge", "Challenge another user")
                            .addOption(OptionType.USER, "opponent", "The user you want to face", true),
                    Commands.slash("shoot", "Choose what place to shoot!")
                            .addOption(OptionType.STRING, "coords", "The coordinates of where you are shooting", true),
                    Commands.slash("setup", "Place your ships, ready to play!")
                            .addOptions(new OptionData(OptionType.STRING, "5lengthstart", "Designate the start of your carrier", true),
                                    new OptionData(OptionType.STRING, "5lengthend", "Designate the end of your carrier", true),
                                    new OptionData(OptionType.STRING, "4lengthstart", "Designate the start of your battleship", true),
                                    new OptionData(OptionType.STRING, "4lengthend", "Designate the end of your battleship", true),
                                    new OptionData(OptionType.STRING, "3lengthstart1", "Designate the start of your destroyer", true),
                                    new OptionData(OptionType.STRING, "3lengthend1", "Designate the end of your destroyer", true),
                                    new OptionData(OptionType.STRING, "3lengthstart2", "Designate the start of your submarine", true),
                                    new OptionData(OptionType.STRING, "3lengthend2", "Designate the end of your submarine", true),
                                    new OptionData(OptionType.STRING, "2lengthstart", "Designate the start of your dinghy", true),
                                    new OptionData(OptionType.STRING, "2lengthend", "Designate the end of your dinghy", true)),
                    Commands.slash("setuptest", "Place a test ship, for easy testing!"),

                    Commands.slash("connectfourchallenge", "Challenge a friend to connect four!")
                            .addOption(OptionType.USER, "opponent", "The friend you are challenging", true),
                    Commands.slash("addtoken", "Add a token to a column in connect four")
                            .addOption(OptionType.INTEGER, "column", "Which column?", true),
                    Commands.slash("testtoken", "Test the connect four game!")
                    ).queue();

            jda.addEventListener(new MessageHandler());

            GameManager.resetBattleships();
            GameManager.resetConnectFour();
        } catch (LoginException e) {
            System.err.println("Failed to create JDA!");
            e.printStackTrace();
        }
    }
}
