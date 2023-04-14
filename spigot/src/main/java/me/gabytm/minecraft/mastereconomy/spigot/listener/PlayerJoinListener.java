package me.gabytm.minecraft.mastereconomy.spigot.listener;

import me.gabytm.minecraft.mastereconomy.spigot.MasterEconomySpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final MasterEconomySpigot plugin;

    public PlayerJoinListener(MasterEconomySpigot plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(final PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.getServerMessenger().requestBalances(event.getPlayer().getUniqueId()));
    }

}
