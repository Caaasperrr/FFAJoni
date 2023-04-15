package me.casper.ffa.listener;

import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeadEvent implements Listener {

    private final FFAConfig ffaConfig;

    public DeadEvent(FFAConfig ffaConfig) {
        this.ffaConfig = ffaConfig;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.spigot().respawn();
        Location location = new Location(Bukkit.getWorld("world"), ffaConfig.getConfiguration().getDouble("spawn.x"),
                ffaConfig.getConfiguration().getDouble("spawn.y"), ffaConfig.getConfiguration().getDouble("spawn.z"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.yaw"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.pitch"));
        Bukkit.getScheduler().runTaskLater(ffaConfig.getPlugin(), () -> {
            player.teleport(location);
        }, 5L);
        event.setDeathMessage(null);
        event.setKeepInventory(true);
    }
}
