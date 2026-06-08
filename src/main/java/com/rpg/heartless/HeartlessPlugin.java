package com.rpg.heartless;

import com.rpg.heartless.commands.WeaponCommand;
import com.rpg.heartless.commands.WeaponGuiCommand;
import com.rpg.heartless.listeners.DeathListener;
import com.rpg.heartless.listeners.PlayerSessionListener;
import com.rpg.heartless.listeners.WeaponListener;
import com.rpg.heartless.managers.ChargeManager;
import com.rpg.heartless.managers.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class HeartlessPlugin extends JavaPlugin {

    private static HeartlessPlugin instance;
    private ChargeManager chargeManager;
    private CooldownManager cooldownManager;
    private NamespacedKey weaponKey;
    private FileConfiguration weaponsConfig;
    private File weaponsFile;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadWeaponsConfig();

        this.weaponKey = new NamespacedKey(this, "heartless_weapon");
        this.chargeManager = new ChargeManager(this);
        this.cooldownManager = new CooldownManager(this);

        Bukkit.getPluginManager().registerEvents(new WeaponListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSessionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);

        WeaponCommand cmd = new WeaponCommand(this);
        getCommand("heartlessweapon").setExecutor(cmd);
        getCommand("heartlessreload").setExecutor(cmd);

        WeaponGuiCommand guiCmd = new WeaponGuiCommand(this);
        Bukkit.getPluginManager().registerEvents(guiCmd, this);
        getCommand("heartlessgui").setExecutor(guiCmd);

        getLogger().info("HeartlessSkills enabled.");
    }

    @Override
    public void onDisable() {
        if (chargeManager != null) chargeManager.clearAll();
        getLogger().info("HeartlessSkills disabled.");
    }

    public void loadWeaponsConfig() {
        weaponsFile = new File(getDataFolder(), "weapons.yml");
        if (!weaponsFile.exists()) saveResource("weapons.yml", false);
        weaponsConfig = YamlConfiguration.loadConfiguration(weaponsFile);
    }

    public static HeartlessPlugin getInstance() { return instance; }
    public ChargeManager getChargeManager()     { return chargeManager; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public NamespacedKey getWeaponKey()         { return weaponKey; }
    public FileConfiguration getWeaponsConfig() { return weaponsConfig; }
}
