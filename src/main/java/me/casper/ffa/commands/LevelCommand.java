package me.casper.ffa.commands;

import lombok.RequiredArgsConstructor;
import me.casper.ffa.mysql.CurrencyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LevelCommand implements CommandExecutor {

    private final CurrencyManager currencyManager;
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        Player player = (Player) cs;
        player.sendMessage("§aDu bist Level: " + currencyManager.getCurrency(player.getUniqueId().toString()).getNow(0) / 10);
        return false;
    }
}
