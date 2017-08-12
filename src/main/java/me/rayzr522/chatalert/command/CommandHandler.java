package me.rayzr522.chatalert.command;

import me.rayzr522.chatalert.ChatAlert;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    private static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "ChatAlert" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD;
    private static final String BAR = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "---------------" + ChatColor.RESET;

    private ChatAlert plugin;

    public CommandHandler(ChatAlert plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ChatAlert.command")) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You need the permission 'ChatAlert.command' to do that.");
            return true;
        }

        if (args.length < 1) {
            showUsage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (plugin.reload()) {
                sender.sendMessage(PREFIX + "Config reloaded successfully!");
            } else {
                sender.sendMessage(PREFIX + ChatColor.RED + "Config failed to load properly, please check the console for details.");
            }
        } else {
            showUsage(sender);
        }

        return true;
    }

    private void showUsage(CommandSender sender) {
        sender.sendMessage(BAR + ChatColor.YELLOW + " ChatAlert " + BAR);
        sender.sendMessage(PREFIX + "Commands:");
        sender.sendMessage(PREFIX + "- /chatalert reload - " + ChatColor.RED + "Reloads the config");
        sender.sendMessage(PREFIX + "Permissions:");
        sender.sendMessage(PREFIX + "- ChatAlert.command - " + ChatColor.RED + "Allows usage of /chatalert");
        sender.sendMessage(PREFIX + "- ChatAlert.notify  - " + ChatColor.RED + "Will be notified of chat alerts");
    }
}
