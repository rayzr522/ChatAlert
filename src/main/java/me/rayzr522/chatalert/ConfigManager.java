package me.rayzr522.chatalert;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigManager {
    void load(ConfigurationSection config) throws BukkitConfigurationException;
}
