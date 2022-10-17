package ua.mani123.discord.event;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import ua.mani123.CBot;
import ua.mani123.discord.interaction.interactionUtils;
import ua.mani123.discord.interaction.interactions.CommandInteraction;

import java.util.ArrayList;

public class ReadyBot extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        ArrayList<CommandData> commandData = new ArrayList<>();
        for (CommandInteraction command : interactionUtils.getCommands()) {
            if (command.getBotIds().isEmpty() && command.getBotIds().contains(event.getJDA().getSelfUser().getId()) && !command.isOnlyGuild()) {
                commandData.add(command.getCommand());
            }
        }
        event.getJDA().updateCommands().addCommands(commandData).queue();
        CBot.getLog().info(commandData.size() + " global commands added to " + event.getJDA().getSelfUser().getName());
    }
}
