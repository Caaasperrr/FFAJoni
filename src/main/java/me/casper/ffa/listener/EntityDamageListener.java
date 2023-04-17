package me.casper.ffa.listener;

import me.casper.ffa.Main;
import me.casper.ffa.mysql.CurrencyManager;
import me.casper.ffa.mysql.DeathsManager;
import me.casper.ffa.mysql.KillsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageListener implements Listener {
    private final CurrencyManager manager = Main.getInstance().getCurrencyManager();
    private final KillsManager killsManager = Main.getInstance().getKillsManager();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (player.getKiller() != null && player.getHealth() - event.getFinalDamage() <= 0) {
                manager.addCurrency(player.getKiller().getUniqueId().toString(), 2).join();
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 3, true, false));
                killsManager.addKills(player.getKiller().getUniqueId().toString(), 1).join();
            }
        }
    }
}