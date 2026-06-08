package com.rpg.heartless.listeners;

import com.rpg.heartless.HeartlessPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerSessionListener implements Listener {

    private final HeartlessPlugin plugin;

    public PlayerSessionListener(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getChargeManager().initBar(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getChargeManager().removeBar(event.getPlayer());
        plugin.getCooldownManager().clearPlayer(event.getPlayer().getUniqueId());
    }
}
