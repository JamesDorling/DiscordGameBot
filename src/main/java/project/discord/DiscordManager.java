package project.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordManager {
    public static void setupDiscord() {
        DiscordApi api = new DiscordApiBuilder()
                .login().join();

        api.addMessageCreateListener(event -> {
            if(event.getMessageContent().equalsIgnoreCase("!battleships")) {
                //api.
            }
        });

        //api.
    }
}
