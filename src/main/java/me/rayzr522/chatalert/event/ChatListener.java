package me.rayzr522.chatalert.event;

import me.rayzr522.chatalert.ChatAlert;
import me.rayzr522.chatalert.util.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ChatListener implements Listener {
    private ChatAlert plugin;

    public ChatListener(ChatAlert plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        if (!plugin.isSetUp()) return;

        List<String> matches = plugin.getFilterManager().findMatches(e.getMessage());

        if (matches.size() > 0) {
            JSONMessage message = plugin.getMessageHandler().getMessage(e.getPlayer(), joinAndLimitTo(matches, ", ", 9, "(more)"));

            getOnlineStaff().forEach(message::send);
        }
    }

    private List<Player> getOnlineStaff() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(this::hasPermission)
                .collect(Collectors.toList());
    }

    private boolean hasPermission(Player player) {
        return player.hasPermission("ChatAlert.notify");
    }

    private String joinAndLimitTo(List<String> list, String joiner, int limit, String etc) {
        List<String> limitedList = list;

        if (limitedList.size() > limit) {
            limitedList = limitedList.subList(0, limit);
            limitedList.add(etc);
        }

        return limitedList.stream().collect(Collectors.joining(joiner));
    }
}
