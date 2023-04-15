package me.casper.ffa.listener;

import me.casper.ffa.Main;
import me.casper.ffa.mysql.CurrencyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class EntityDamageEvent implements Listener {
    private final CurrencyManager manager = Main.getInstance().getCurrencyManager();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (player.getKiller() != null && player.getHealth() - event.getFinalDamage() <= 0) {
                manager.addCurrency(player.getKiller().getUniqueId().toString(), 2).join();
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 3, true, false));
            }
        }
    }
}