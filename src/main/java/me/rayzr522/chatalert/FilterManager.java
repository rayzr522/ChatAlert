package me.rayzr522.chatalert;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.stream.Collectors;

public class FilterManager implements ConfigManager {
    private List<String> words;

    public void load(ConfigurationSection config) throws BukkitConfigurationException {
        this.words = config.getStringList("words");
    }

    public List<String> findMatches(String input) {
        String lower = input.toLowerCase();

        return words.stream()
                .filter(word -> lower.contains(word.toLowerCase()))
                .collect(Collectors.toList());
    }
}
