package project.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import project.config.Config;
import project.discord.listeners.MessageListener;
import project.games.battleships.BattleshipsGame;

import javax.security.auth.login.LoginException;

public class DiscordManager {
    public static void runBot() {
        try {
            JDA jda = JDABuilder.createDefault(Config.botToken())
                    .setActivity(Activity.watching("you play battleships"))
                    .build();

            jda.upsertCommand("ping", "Calculate ping of the bot").queue();

            jda.upsertCommand("challenge", "Challenge another user").queue();

            jda.addEventListener(new MessageListener());

        } catch (LoginException e) {
            e.printStackTrace();
        }
        //BattleshipsGame.run();
    }
}
