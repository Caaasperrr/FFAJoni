package me.casper.ffa.listener;

import me.casper.ffa.Main;
import me.casper.ffa.mysql.DeathsManager;
import me.casper.ffa.mysql.KillsManager;
import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeadListener implements Listener {

    private final FFAConfig ffaConfig;

    private final DeathsManager deathsManager = Main.getInstance().getDeathsManager();

    public DeadListener(FFAConfig ffaConfig) {
        this.ffaConfig = ffaConfig;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.spigot().respawn();
        deathsManager.addDeaths(player.getUniqueId().toString(), 1).join();
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
