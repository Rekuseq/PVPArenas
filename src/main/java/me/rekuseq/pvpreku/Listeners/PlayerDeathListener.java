package me.rekuseq.pvpreku.Listeners;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PVPReku plugin;

    public PlayerDeathListener(PVPReku plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        if(plugin.arenaManager.getArena(p) != null){
            plugin.arenaManager.getArena(p);
        }
    }
}
