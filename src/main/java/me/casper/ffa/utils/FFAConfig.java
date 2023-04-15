package me.casper.ffa.utils;

import lombok.Getter;
import me.casper.ffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class FFAConfig {

    private final Main plugin;
    private FileConfiguration configuration;
    private File file;

    public FFAConfig(Main plugin) {
        this.plugin = plugin;
        this.plugin.getDataFolder().mkdir();
        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.plugin.saveResource("config.yml", false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void saveFile() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
