package ua.mani123.discord.action.filter.filters;

import com.electronwill.nightconfig.core.CommentedConfig;
import java.util.ArrayList;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.session.GenericSessionEvent;
import ua.mani123.discord.action.ActionUtils;
import ua.mani123.discord.action.TempData;
import ua.mani123.discord.action.filter.Filter;

public class USER implements Filter {

  private final ArrayList<String> actionNames;
  private final ArrayList<String> beforeActionNames;
  boolean isBlackList;


  public USER(CommentedConfig config) {
    this.isBlackList = config.getOrElse("isBlackList", false);
    this.actionNames = config.getOrElse("filter-actions", new ArrayList<>());
    this.beforeActionNames = config.getOrElse("before-filter-actions", new ArrayList<>());
  }

  @Override
  public boolean canRun(GenericInteractionCreateEvent event, TempData tempData) {
    beforeActionNames.forEach(s -> ActionUtils.getActionMap().get(s).forEach(action -> action.run(event, tempData)));
    boolean answer = tempData.getUserSnowflakes().contains(event.getMember());
      if (isBlackList) {
        return !answer;
      } else {
        return answer;
      }
  }

  @Override
  public boolean canRun(GenericGuildEvent event, TempData tempData) {
    boolean answer = tempData.getUserSnowflakes().containsAll(event.getGuild().getMembers());
    if (isBlackList) {
      return !answer;
    } else {
      return answer;
    }
  }

  @Override
  public boolean canRun(GenericSessionEvent genericSessionEvent, TempData tempData) {
    return true;
  }

  @Override
  public ArrayList<String> getFilterActionIds() {
    return this.actionNames;
  }
}
