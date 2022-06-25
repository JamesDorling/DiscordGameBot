package project.discord.listeners;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import project.discord.output.MessageSender;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        if (event.isFromType(ChannelType.TEXT))
        {
            System.out.printf("%s %s: %s\n",event.getMessage().getTextChannel().getName(), event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
            if(event.getChannel().getName().equals("fish")) {
                System.out.println("Sending Message");
                MessageSender.selfDestruct(event.getChannel(), "Excellent Penis, sir.");
                event.getChannel().sendMessage("nice balls").queue();
            }
            else{
                System.out.println("Not correct channel!");
            }
        }
        else
        {

        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        switch(event.getName()) {
            case "ping" -> pingEvent(event);
            case "challenge" -> challengeEvent(event);
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

    }
}
