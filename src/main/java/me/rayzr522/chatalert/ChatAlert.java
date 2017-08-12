package me.rayzr522.chatalert;

import me.rayzr522.chatalert.command.CommandHandler;
import me.rayzr522.chatalert.event.ChatListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatAlert extends JavaPlugin {
    private FilterManager filterManager;
    private MessageHandler messageHandler;

    private boolean setUp = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        filterManager = new FilterManager();
        messageHandler = new MessageHandler();

        getCommand("chatalert").setExecutor(new CommandHandler(this));

        reload();
    }

    public boolean reload() {
        saveDefaultConfig();
        reloadConfig();

        return setUp = load(filterManager, getConfig()) && load(messageHandler, getConfig());
    }

    private boolean load(ConfigManager configManager, ConfigurationSection config) {
        try {
            configManager.load(config);
            return true;
        } catch (BukkitConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public boolean isSetUp() {
        return setUp;
    }
}
