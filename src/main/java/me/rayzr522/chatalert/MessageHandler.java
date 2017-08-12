package me.rayzr522.chatalert;

import me.rayzr522.chatalert.util.JSONMessage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MessageHandler implements ConfigManager {
    private String notificationMessage;
    private String buttonText;
    private String buttonCommand;

    public void load(ConfigurationSection config) throws BukkitConfigurationException {
        notificationMessage = tryGet(config, "notification-message");
        buttonText = tryGet(config, "button-text");
        buttonCommand = tryGet(config, "button-command");
    }

    private String tryGet(ConfigurationSection config, String key) {
        if (!config.isString(key)) {
            throw new BukkitConfigurationException(key + " property was missing");
        }

        return config.getString(key);
    }

    private String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private String applyPlaceholders(String input, Player offender, String words) {
        return input.replace("<player>", offender.getName())
                .replace("<words>", words);
    }

    public JSONMessage getMessage(Player offender, String words) {
        return JSONMessage.create(colorize(applyPlaceholders(notificationMessage, offender, words)))
                .then(colorize(applyPlaceholders(buttonText, offender, words)))
                .runCommand(applyPlaceholders(buttonCommand, offender, words));
    }
}
