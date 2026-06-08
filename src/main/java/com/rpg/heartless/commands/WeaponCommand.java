package com.rpg.heartless.commands;

import com.rpg.heartless.HeartlessPlugin;
import com.rpg.heartless.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WeaponCommand implements CommandExecutor {

    private final HeartlessPlugin plugin;

    public WeaponCommand(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heartlessreload")) {
            plugin.reloadConfig();
            plugin.loadWeaponsConfig();
            sender.sendMessage("§aHeartlessSkills config reloaded.");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("heartlessweapon")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /heartlessweapon <player> <weaponId>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) { sender.sendMessage("§cPlayer not found: " + args[0]); return true; }
            ItemStack item = ItemUtils.buildWeapon(args[1]);
            if (item == null) { sender.sendMessage("§cUnknown weapon ID: " + args[1]); return true; }
            target.getInventory().addItem(item);
            target.sendMessage("§aYou received: §r" + item.getItemMeta().getDisplayName());
            sender.sendMessage("§aGave §e" + args[1] + " §ato §e" + target.getName());
            return true;
        }
        return false;
    }
}
