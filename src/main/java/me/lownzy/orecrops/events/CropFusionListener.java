package me.lownzy.orecrops.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;

public class CropFusionListener implements Listener {

    public static HashMap<Location, List<UUID>> cropArmorstand = new HashMap<>();

    @EventHandler
    void onPlayerBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();

        if (block.getType() == Material.WHEAT ) {

            List<UUID> armorstandList = new ArrayList<>();
            //Title

            Location WheatLocation = event.getBlock().getLocation();
            Location location = event.getBlock().getLocation();
            location.setY(location.getBlockY());
            location.setX(location.getBlockX() + 0.5D);
            location.setZ(location.getBlockZ() + 0.5D);
            Entity entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
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
            entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            ArmorStand progress = (ArmorStand) entity;
            progress.setVisible(false);
            progress.setGravity(false);
            progress.setSmall(true);
            progress.setCustomNameVisible(true);
            progress.setCustomName(ChatColor.BLACK + "[" + ChatColor.RED + "=====" + ChatColor.BLACK + "]");

            armorstandList.add(name.getUniqueId()); // is 0 in list
            armorstandList.add(progress.getUniqueId()); // is 1 in list


            cropArmorstand.put(WheatLocation, armorstandList); //Puts location to armor stand


        }
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.WHEAT) {
            Location location = event.getBlock().getLocation();
            if (cropArmorstand.containsKey(location)) {
                //Searches cropArmorstand hashmap for location if it exists it runs the rest
                List<UUID> armorStands = cropArmorstand.get(event.getBlock().getLocation());
                UUID nameUUID = armorStands.get(0);
                UUID progessUUID = armorStands.get(1);
                Entity nameArmorStand = Bukkit.getEntity(nameUUID);
                Entity progressArmorStand = Bukkit.getEntity(progessUUID);
                try {
                    nameArmorStand.remove();
                    progressArmorStand.remove();
                } catch (NullPointerException e) {
                    System.out.println("Armorstands do not exist!");


                }
            }
        }
    }

}
