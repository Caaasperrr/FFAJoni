package me.casper.ffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntitySpawnListener implements Listener{

    @EventHandler
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent event) {
        event.setCancelled(true);
    }

}
