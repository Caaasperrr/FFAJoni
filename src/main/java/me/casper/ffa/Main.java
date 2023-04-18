package me.casper.ffa;

import lombok.Getter;
import me.casper.ffa.commands.*;
import me.casper.ffa.listener.*;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.mysql.DeathsManager;
import me.casper.ffa.mysql.KillsManager;
import me.casper.ffa.mysql.MySQL;
import me.casper.ffa.utils.MySQLConfig;
import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private MySQL mySQL;

    private CurrencyManager currencyManager;
    private KillsManager killsManager;
    private DeathsManager deathsManager;

    public static String prefix = "§7[§cFFA§7] ";

    MySQLConfig mySQLFile;
    FFAConfig ffaConfig = null;

    @Override
    public void onEnable() {

        instance = this;

        mySQLFile = new MySQLConfig(this);
        mySQL = mySQLFile.readData();
        mySQL.connect();
        mySQL.createTables();

        currencyManager = new CurrencyManager(mySQL);
        killsManager = new KillsManager(mySQL);
        deathsManager = new DeathsManager(mySQL);

        register();

        for (int i = 1; i <= 7; i++)
            if (i != 4)
                System.out.println("--------------");
            else
                System.out.println("Server Plugin ist ohne Fehler gestartet!");

    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
    }

    public void register() {
        ffaConfig = new FFAConfig(this);
        /*
        register Commands
         */
        getCommand("setspawn").setExecutor(new SpawnCommand(ffaConfig));
        getCommand("xp").setExecutor(new XpCommand(currencyManager));
        getCommand("level").setExecutor(new LevelCommand(currencyManager));
        getCommand("stats").setExecutor(new StatsCommand(killsManager, deathsManager, currencyManager));
        getCommand("gm").setExecutor(new GamemodeCommand());


        /*
        register Events
         */
        Bukkit.getPluginManager().registerEvents(new JoinListener(currencyManager, killsManager, deathsManager, ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeadListener(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

    }
}