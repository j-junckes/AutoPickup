package dev.junckes.autopickup;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AutoPickupListener implements Listener {

    private final AutoPickupPlugin plugin;

    public AutoPickupListener(AutoPickupPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        if (!killer.hasPermission("autopickup.use")) return;

        if (!plugin.isGlobalEnabled()) return;
        if (!plugin.isPlayerEnabled(killer.getUniqueId())) return;

        for (ItemStack drop : event.getDrops()) {
            if (drop == null) continue;
            HashMap<Integer, ItemStack> leftovers = killer.getInventory().addItem(drop);

            for (ItemStack leftover : leftovers.values()) {
                killer.getWorld().dropItemNaturally(killer.getLocation(), leftover);
            }
        }

        event.getDrops().clear();
    }
}
