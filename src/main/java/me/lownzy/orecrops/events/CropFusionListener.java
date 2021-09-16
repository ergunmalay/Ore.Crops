package me.lownzy.orecrops.events;

import me.lownzy.orecrops.OreCrops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;

public class CropFusionListener implements Listener {

    private final FileConfiguration config = OreCrops.getPlugin(OreCrops.class).getConfig();


    @EventHandler
    void onPlayerBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();

        if (block.getType() == Material.WHEAT ) {

            //Title

            Location WheatLocation = event.getBlock().getLocation();
            Location location = event.getBlock().getLocation();
            location.setY(location.getBlockY());
            location.setX(location.getBlockX() + 0.5D);
            location.setZ(location.getBlockZ() + 0.5D);
            Entity entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
            ArmorStand name = (ArmorStand) entity;
            name.setVisible(false);
            name.setGravity(false);
            name.setSmall(true);
            name.setCustomNameVisible(true);
            name.setCustomName(ChatColor.YELLOW + "Wheat");

            //Progress Bar
            location = event.getBlock().getLocation();
            location.setY(location.getBlockY() - 0.2);
            location.setX(location.getBlockX() + 0.5D);
            location.setZ(location.getBlockZ() + 0.5D);
            entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
            ArmorStand progress = (ArmorStand) entity;
            progress.setVisible(false);
            progress.setGravity(false);
            progress.setSmall(true);
            progress.setCustomNameVisible(true);
            progress.setCustomName(ChatColor.BLACK + "[" + ChatColor.RED + "=====" + ChatColor.BLACK + "]");

            config.set("cropArmorstand.1.x", WheatLocation.getBlockX());
            config.set("cropArmorstand.1.y", WheatLocation.getBlockY());
            config.set("cropArmorstand.1.z", WheatLocation.getBlockZ());
            config.set("cropArmorstand.1.Location", WheatLocation.toString());

            config.set("cropArmorstand.1.nameStand", name.getUniqueId().toString());
            config.set("cropArmorstand.1.progressStand", progress.getUniqueId().toString());

        }
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.WHEAT) {
            String location = event.getBlock().getLocation().toString();
            if (location.equals(config.getString("cropArmorstand.1.Location"))) {
                UUID nameUUID = UUID.fromString(Objects.requireNonNull(config.getString("cropArmorstand.1.nameStand")));
                UUID progressUUID = UUID.fromString(Objects.requireNonNull(config.getString("cropArmorstand.1.progressStand")));
                Entity nameArmorStand = Bukkit.getEntity(nameUUID);
                Entity progressArmorStand = Bukkit.getEntity(progressUUID);
                try {
                    nameArmorStand.remove();
                    progressArmorStand.remove();
                } catch (NullPointerException e) {
                    System.out.println("Armorstands do not exist!");
                }
            } else {
                System.out.println("Normal  " + location);
                System.out.println("Stringged up " + config.getString("cropArmorstand.1.Location"));
            }
        }
    }
}