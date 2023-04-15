package me.casper.ffa;

import lombok.Getter;
import me.casper.ffa.commands.LevelCommand;
import me.casper.ffa.commands.SpawnCommand;
import me.casper.ffa.commands.XpCommand;
import me.casper.ffa.listener.*;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.mysql.MySQL;
import me.casper.ffa.utils.MySQLConfig;
import me.casper.ffa.utils.FFAConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private MySQL mySQL;

    private CurrencyManager currencyManager;

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


        /*
        register Events
         */
        Bukkit.getPluginManager().registerEvents(new JoinListener(currencyManager, ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeadEvent(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakEvent(ffaConfig), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceEvent(ffaConfig), this);

    }
}