package me.casper.ffa.commands;

import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private final FFAConfig ffaConfig;

    public SpawnCommand(FFAConfig ffaConfig) {
        this.ffaConfig = ffaConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();

            ffaConfig.getConfiguration().set("spawn.x", location.getX());
            ffaConfig.getConfiguration().set("spawn.y", location.getY());
            ffaConfig.getConfiguration().set("spawn.z", location.getZ());
            ffaConfig.getConfiguration().set("spawn.yaw", location.getYaw());
            ffaConfig.getConfiguration().set("spawn.pitch", location.getPitch());

            ffaConfig.saveFile();
        }

        return false;
    }

}
