package ua.mani123.discord.event.CustomEvents;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.text.StringSubstitutor;
import org.jetbrains.annotations.NotNull;
import ua.mani123.discord.action.TempData;
import ua.mani123.discord.action.filter.filterUtils;
import ua.mani123.discord.event.EventUtils;
import ua.mani123.discord.interaction.InteractionTypes;
import ua.mani123.discord.interaction.InteractionUtils;
import ua.mani123.discord.interaction.interactions.CommandInteraction;

public class CustomSlashCommandInteraction extends ListenerAdapter {

  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    InteractionUtils.getInteractions().get(InteractionTypes.COMMAND).forEach(interaction -> {
      if (interaction instanceof CommandInteraction commandInteraction) {
        if (event.getName().equals(commandInteraction.getName())){
          TempData tempData = new TempData();
          if (filterUtils.filterCheck(commandInteraction.getFilters(), event, new StringSubstitutor(tempData.getPlaceholders()), tempData)) {
            EventUtils.runActions(commandInteraction.getActionIds(), event, new StringSubstitutor(tempData.getPlaceholders()), tempData);
          }
        }
      }
    });
  }

}
