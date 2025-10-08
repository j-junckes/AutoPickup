package dev.junckes.autopickup;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggle implements CommandExecutor {

    private final AutoPickupPlugin plugin;

    public CommandToggle(AutoPickupPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /autopickup [global|player]");
            return true;
        }

        if (args[0].equalsIgnoreCase("global")) {
            if (!sender.hasPermission("autopickup.toggle")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            boolean newState = !plugin.isGlobalEnabled();
            plugin.setGlobalEnabled(newState);
            sender.sendMessage(ChatColor.GREEN + "Global auto-pickup is now " + (newState ? "enabled" : "disabled"));
            return true;
        }

        if (args[0].equalsIgnoreCase("player")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this.");
                return true;
            }

            if (!player.hasPermission("autopickup.toggle")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            plugin.togglePlayer(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "Your auto-pickup is now " +
                    (plugin.isPlayerEnabled(player.getUniqueId()) ? "enabled" : "disabled"));
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Usage: /autopickup [global|player]");
        return true;
    }
}
