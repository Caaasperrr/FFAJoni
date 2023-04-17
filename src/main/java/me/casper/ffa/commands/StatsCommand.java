package me.casper.ffa.commands;

import lombok.RequiredArgsConstructor;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.mysql.DeathsManager;
import me.casper.ffa.mysql.KillsManager;
import me.casper.ffa.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

/*
Kills, Deaths, Level,Xp
 */

/*
----------------------Important------------------------
Kills deaths level und xp müssen in der lore angezeigt werden!
----------------------Important------------------------
 */

@RequiredArgsConstructor
public class StatsCommand implements CommandExecutor {

    private final KillsManager killsManager;
    private final DeathsManager deathsManager;
    private final CurrencyManager currencyManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(player, 9*6, "§cStats");

            if(killsManager.getKills(player.getUniqueId().toString()).join() == 0) {
                inventory.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§aKills").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setLore(String.valueOf(killsManager.getKills(player.getUniqueId().toString()).join())).build());
            }else if (killsManager.getKills(player.getUniqueId().toString()).join() > 0){
                inventory.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§cKills").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setLore(String.valueOf(killsManager.getKills(player.getUniqueId().toString()).join())).build());
            } else if (killsManager.getKills(player.getUniqueId().toString()).join() > 100) {
                inventory.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§4Kills").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setLore(String.valueOf(killsManager.getKills(player.getUniqueId().toString()).join())).build());
            }

            if (deathsManager.getDeaths(player.getUniqueId().toString()).join() == 0) {
                inventory.setItem(22, new ItemBuilder(Material.BONE).setDisplayname("§aDeaths").setLore(String.valueOf(deathsManager.getDeaths(player.getUniqueId().toString()).join())).build());
            } else if (deathsManager.getDeaths(player.getUniqueId().toString()).join() > 0) {
                inventory.setItem(22, new ItemBuilder(Material.BONE).setDisplayname("§cDeaths").setLore(String.valueOf(deathsManager.getDeaths(player.getUniqueId().toString()).join())).build());
            } else if (deathsManager.getDeaths(player.getUniqueId().toString()).join() > 100) {
                inventory.setItem(22, new ItemBuilder(Material.BONE).setDisplayname("§4Deaths").setLore(String.valueOf(deathsManager.getDeaths(player.getUniqueId().toString()).join())).build());
            }

            inventory.setItem(29, new ItemBuilder(Material.EXP_BOTTLE).setDisplayname("§aXP").setLore(String.valueOf(currencyManager.getCurrency(player.getUniqueId().toString()).join())).build());
            inventory.setItem(31, new ItemBuilder(Material.PAPER).setDisplayname("§aLevel").setLore(String.valueOf(currencyManager.getCurrency(player.getUniqueId().toString()).join() / 10)).build());

            player.openInventory(inventory);
        }

        return false;
    }
}
