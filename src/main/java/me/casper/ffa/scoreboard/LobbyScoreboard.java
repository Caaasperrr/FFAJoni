package me.casper.ffa.scoreboard;

import me.casper.ffa.Main;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.utils.Rank;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;

public class LobbyScoreboard {
    private final Player player;
    private final CurrencyManager manager = Main.getInstance().getCurrencyManager();


    public LobbyScoreboard(final Player player) {
        this.player = player;
    }

    public static Rank getRank(final Player user) {
        for (Rank rank : Rank.values()) {
            if (user.hasPermission("rank" + rank.name()))
                return rank;
        }

        return Rank.PLAYER;
    }

    public static void loadTeam(final Scoreboard scoreboard) {
        scoreboard.registerNewTeam("00000000Ghost");

        scoreboard.getTeam("00000000Ghost").setPrefix("§8VANISHED | ");
        scoreboard.getTeam("00000000Ghost").setCanSeeFriendlyInvisibles(true);

        Arrays.stream(Rank.values()).forEach(rank -> {
            scoreboard.registerNewTeam(rank.getTeam());
            scoreboard.getTeam(rank.getTeam()).setPrefix(rank.getPrefix());
        });
    }

    public void setScoreboard() {
        final BPlayerBoard board = (ScoreboardAPI.instance().getBoard(player) != null) ? ScoreboardAPI.instance()
                .getBoard(player) : ScoreboardAPI.instance().createBoard(player, "§cFFA");

        board.setScore(" ", 6);
        board.setScore("§7Rank §8• " + getRank(player).getName(), 5);
        board.setScore("§7Level §8• §2" + manager.getCurrency(player.getUniqueId().toString()).getNow(0) / 10, 4);
        //        try {
//            board.setScore("Level: §2" + manager.getLevel(player.getUniqueId()), 4);
//            board.setScore("Coins: §2" + manager.getCoins(player.getUniqueId()), 3);
//        } catch (SQLException | ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

        int counter = 0;

        board.setScore("  ", 2);
        board.setScore("§7Players §8• §2" + Bukkit.getOnlinePlayers().size(), 1);
        board.setScore("§7Server §8• §2" + Bukkit.getServerName(), 0);
        setPrefix();
    }

    public void update() {
        BPlayerBoard board = ScoreboardAPI.instance().getBoard(player);
        board.setScore("§7Rank §8• " + getRank(player).getName(), 5);
        board.setScore("§7Level §8• §2" + manager.getCurrency(player.getUniqueId().toString()).join() / 10, 4);
        //        try {
//            board.setScore("Level: §2" + manager.getLevel(player.getUniqueId()), 4);
//            board.setScore("Coins: §2" + manager.getCoins(player.getUniqueId()), 3);
//        } catch (SQLException | ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
        int counter = 0;
        board.setScore("§7Players §8• §2" + Bukkit.getOnlinePlayers().size(), 1);
    }

    public void startUpdating() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this::update, 0L, 5 * 20L);
    }

    public void startUpdatingPrefix() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this::setPrefix, 0L, 20L);
    }

    public void setPrefix() {
        if (player == null || !player.isOnline()) return;

        final Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard.getTeam("00000000Ghost") == null) {
            loadTeam(scoreboard);
        }
        scoreboard.getTeam(getRank(player).getTeam()).addPlayer(player);

        Bukkit.getOnlinePlayers().forEach(all -> {
            if (all.getUniqueId() == player.getUniqueId()) return;
            scoreboard.getTeam(getRank(all).getTeam()).addPlayer(all);
        });
    }

    public void sendTablist(Player player, String title, String subTitle) {
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent tabSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }

}
