package me.casper.ffa.listener;

import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPlaceListener implements Listener {

    private final FFAConfig ffaConfig;

    public BlockPlaceListener(FFAConfig ffaConfig) {
        this.ffaConfig = ffaConfig;
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Location location = new Location(Bukkit.getWorld("world"), ffaConfig.getConfiguration().getDouble("spawn.x"),
                ffaConfig.getConfiguration().getDouble("spawn.y"), ffaConfig.getConfiguration().getDouble("spawn.z"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.yaw"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.pitch"));
        if (location.distance(event.getPlayer().getLocation()) < 5) {
            event.setCancelled(true);
        }
    }

}
