package me.casper.ffa.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onJoin(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getName().equals("§cStats")) {
            event.setCancelled(true);
        }
    }

}
