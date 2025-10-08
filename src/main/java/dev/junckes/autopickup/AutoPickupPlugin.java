package dev.junckes.autopickup;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class AutoPickupPlugin extends JavaPlugin {

    private static AutoPickupPlugin instance;

    private boolean globalEnabled;
    private final Set<UUID> disabledPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadConfig();

        getServer().getPluginManager().registerEvents(new AutoPickupListener(this), this);
        Objects.requireNonNull(getCommand("autopickup")).setExecutor(new CommandToggle(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static AutoPickupPlugin getInstance() {
        return instance;
    }

    public void loadConfig() {
        FileConfiguration config = getConfig();
        globalEnabled = config.getBoolean("enabled", true);
    }

    public void saveGlobalSetting() {
        getConfig().set("enabled", globalEnabled);
        saveConfig();
    }

    public boolean isGlobalEnabled() {
        return globalEnabled;
    }

    public void setGlobalEnabled(boolean enabled) {
        this.globalEnabled = enabled;
        saveGlobalSetting();
    }

    public boolean isPlayerEnabled(UUID uuid) {
        return !disabledPlayers.contains(uuid);
    }

    public void togglePlayer(UUID uuid) {
        if (disabledPlayers.contains(uuid)) {
            disabledPlayers.remove(uuid);
        } else {
            disabledPlayers.add(uuid);
        }
    }
}
