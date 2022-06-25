package project.discord.output;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.RestAction;

import static java.util.concurrent.TimeUnit.*;

public class MessageSender {
    public static RestAction<Void> selfDestruct(MessageChannel channel, String content) {
        return channel.sendMessage("The following message will destroy itself in 1 minute!")
                .delay(10, SECONDS) // edit 10 seconds later
                .flatMap((it) -> it.editMessage(content))
                .delay(1, MINUTES) // delete 1 minute later
                .flatMap(Message::delete).delay(1, MINUTES);
    }
}
