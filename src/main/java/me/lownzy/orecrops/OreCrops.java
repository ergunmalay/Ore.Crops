package me.lownzy.orecrops;

import me.lownzy.orecrops.events.CropFusionListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;


public final class OreCrops extends JavaPlugin {

    private PluginManager pluginManager;
    private static File file;
    private static FileConfiguration customFile;

    @Override
    public void onEnable() {
        pluginManager = Bukkit.getPluginManager();

        System.out.println("==============================="
                + "########Ore Crop Loaded########"
                + "===============================");

        //Register Listener
        pluginManager.registerEvents(new CropFusionListener(), this);

        ///////////////////////////////////////////////////////////////////////
        //getConfig();

    }

    public void onDisable() {
        saveConfig();
    }
}