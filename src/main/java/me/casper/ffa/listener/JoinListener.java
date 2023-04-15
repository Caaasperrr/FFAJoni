package me.casper.ffa.listener;

import lombok.RequiredArgsConstructor;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.scoreboard.LobbyScoreboard;
import me.casper.ffa.utils.FFAConfig;
import me.casper.ffa.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final CurrencyManager manager;
    private final FFAConfig ffaConfig;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setGameMode(GameMode.SURVIVAL);
        event.setJoinMessage("§a>> §7" + player.getName());
        Location location = new Location(Bukkit.getWorld("world"), ffaConfig.getConfiguration().getDouble("spawn.x"),
                ffaConfig.getConfiguration().getDouble("spawn.y"), ffaConfig.getConfiguration().getDouble("spawn.z"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.yaw"),
                (float) ffaConfig.getConfiguration().getDouble("spawn.pitch"));
        player.teleport(location);

        new LobbyScoreboard(player).setScoreboard();
        new LobbyScoreboard(player).startUpdating();
        new LobbyScoreboard(player).startUpdatingPrefix();
        new LobbyScoreboard(player).sendTablist(player, "§cFFA", "§6by Caaasper");

        Bukkit.getOnlinePlayers().forEach(all -> new LobbyScoreboard(all).setPrefix());

        player.getInventory().setBoots(
                new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§bDiamond Boots").build());
        player.getInventory().setChestplate(
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§bDiamond Chestplate").build());
        player.getInventory().setHelmet(
                new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§bDiamond Helm").build());
        player.getInventory().setLeggings(
                new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§bDiamond Hose").build());
        player.getInventory().setItem(0,
                new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§bDiamond Schwert").build());
        player.getInventory().setItem(1,
                new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayname("§bDiamond Schwert").build());
        player.getInventory().setItem(2, new ItemBuilder(Material.BOW).setDisplayname("§7Bogen").addEnchantement(1,
                Enchantment.ARROW_INFINITE).build());
        player.getInventory().setItem(9, new ItemBuilder(Material.ARROW).setDisplayname("§7Pfeil").build());

        if (!manager.isExisting(player.getUniqueId().toString()).join()) {
            manager.createUser(player.getUniqueId().toString()).join();
        }
    }
}