package play.minecraft.banstick;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Objects;

public final class Banstick extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void onPIE(PlayerInteractEvent e) {
        if (e.getPlayer().isOp() && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
            Location loc = e.getPlayer().getEyeLocation();
            Vector baseVec = loc.getDirection();
            for (int i = 0; i< 100; i++) {
                Vector vec = baseVec.clone().multiply(i).add(loc.toVector());
                Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, vec.getX(), vec.getY(), vec.getZ(), 1);
                for (Entity entity : loc.getWorld().getNearbyEntities(vec.toLocation(loc.getWorld()), 0.5, 0.5, 0.5)) {
                    if(entity.getUniqueId() != e.getPlayer().getUniqueId() && entity instanceof Player) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(entity.getName(), "Banned by a BanStick", null, "");
                        ((Player) entity).kickPlayer(ChatColor.GOLD + "Banned");
                    }
                }
            }
        }
    }
}


