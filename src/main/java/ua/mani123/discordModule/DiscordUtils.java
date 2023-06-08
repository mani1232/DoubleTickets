package ua.mani123.discordModule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.mani123.CustomBot;
import ua.mani123.config.ConfigUtils;
import ua.mani123.config.Objects.BotConfig;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class DiscordUtils {

    private final static Logger logger = LoggerFactory.getLogger(DiscordUtils.class);

    BotConfig botConfig;

    ArrayList<ShardManager> discordBots = new ArrayList<>();

    public DiscordUtils init(String defaultFolder) {
        botConfig = new ConfigUtils(defaultFolder + "/BotConfig.toml").loadFileConfig(new BotConfig());
        return this;
    }

    public DiscordUtils enableBots() {
        botConfig.getDiscordBots().forEach(discordBot -> {
            try {
                ShardManager shardManager = DefaultShardManagerBuilder.createDefault(discordBot.getBotToken()).build();
                discordBots.add(shardManager);
                discordBot.setBotId(shardManager.getShards().get(0).getSelfUser().getId());
            } catch (InvalidTokenException ignored) {
                logger.error(String.format(CustomBot.getLang().getFiledLoadDiscordBot(), discordBot.getBotId(), discordBot.getBotToken()));
            }
        });
        botConfig.getUtils().updateConfig(botConfig);
        logger.info(String.format(CustomBot.getLang().getEnabledDiscordBot(), discordBots.size()));
        return this;
    }

}
