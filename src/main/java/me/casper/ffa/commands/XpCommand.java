package me.casper.ffa.commands;

import lombok.RequiredArgsConstructor;
import me.casper.ffa.mysql.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class XpCommand implements CommandExecutor {

    private final CurrencyManager currencyManager;

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        Player player = (Player)cs;

        if(args.length == 0){
            player.sendMessage("§7Dein Exp beträgt: §6§l" + currencyManager.getCurrency(player.getUniqueId().toString()).join());
        }else if(args.length == 3){
            // money add 1bluenitrox 1
            if(args[0].equalsIgnoreCase("add")){
                Player addplayer = Bukkit.getPlayer(args[1]);
                if(addplayer != null) {
                    currencyManager.addCurrency(addplayer.getUniqueId().toString(), Integer.parseInt(args[2]));
                    player.sendMessage("EXP wurde hinzugefügt");
                }else {
                    player.sendMessage("player not online");
                }
            }
            else if(args[0].equalsIgnoreCase("set")){
                Player addplayer = Bukkit.getPlayer(args[1]);
                if(addplayer != null) {
                    currencyManager.setCurrency(addplayer.getUniqueId().toString(), Integer.parseInt(args[2]));
                    player.sendMessage("EXP wurde gesetzt");
                }else {
                    player.sendMessage("player not online");
                }
            }
        }else {
            player.sendMessage("ERROR");
        }
        return false;
    }
}
