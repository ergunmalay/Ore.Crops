package me.lownzy.orecrops;

import me.lownzy.orecrops.events.CropFusionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import static me.lownzy.orecrops.events.CropFusionListener.cropArmorstand;

public final class OreCrops extends JavaPlugin {

    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        pluginManager = Bukkit.getPluginManager();

        System.out.println("==============================="
                           +"########Ore Crop Loaded########"
                           +"===============================");

        //Config Save
        saveDefaultConfig(); // <-- Create Config.yml

        if (this.getConfig().contains("data"))
            this.loadLocation();




        //Register Commands

        //Register Listener
        pluginManager.registerEvents(new CropFusionListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (!cropArmorstand.isEmpty()) {
            this.saveLocation();
        }
    }
    public void saveLocation() {


        }

    public void loadLocation() {


    }
}
