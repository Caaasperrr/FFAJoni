package me.casper.ffa.commands;

import me.casper.ffa.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp()) {
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "§6Usage: §c/gm <1, 2, 3, 0>");
                } else if (args[0].equals("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Main.prefix + "§aCreative");
                }else if (args[0].equals("2")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Main.prefix + "§aAdventure");
                }else if (args[0].equals("3")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Main.prefix + "§aSpectator");
                }else if (args[0].equals("0")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Main.prefix + "§aSurvival");
                }else {
                    player.sendMessage(Main.prefix + "§6Usage: §c/gm <1, 2, 3, 0>");
                }
            }else {
                player.sendMessage(Main.prefix + "§cDafür hast du keine Rechte");
            }
        }

        return false;
    }
}
