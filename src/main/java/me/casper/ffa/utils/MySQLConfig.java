package me.casper.ffa.utils;

import lombok.Getter;
import me.casper.ffa.Main;
import me.casper.ffa.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class MySQLConfig {
    private final Main plugin;
    private FileConfiguration configuration;
    private File file;

    public MySQLConfig(Main plugin) {
        this.plugin = plugin;
        this.plugin.getDataFolder().mkdir();
        this.file = new File(plugin.getDataFolder(), "mysql.yml");
        if (!file.exists()) {
            this.plugin.saveResource("mysql.yml", false);
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

    public MySQL readData() {
        return new MySQL(configuration.getString("host"), configuration.getInt("port"),
                configuration.getString("database"), configuration.getString("username"),
                configuration.getString("password"));
    }
}