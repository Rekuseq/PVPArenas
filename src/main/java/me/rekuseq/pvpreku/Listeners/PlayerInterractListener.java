package me.rekuseq.pvpreku.Listeners;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInterractListener implements Listener {

    private final PVPReku plugin;


    public PlayerInterractListener(PVPReku plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerInterract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.OAK_SIGN) {
            if (plugin.arenaManager.getArena(player) != null) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Sign sign = (Sign) event.getClickedBlock().getState();
                    if (sign.getLine(0).equalsIgnoreCase("TEAM")) {
                        if (sign.getLine(1).equalsIgnoreCase("RED")) {
                            plugin.arenaManager.getArena(player).addPlayerToTeamA(player);
                        } else if (sign.getLine(1).equalsIgnoreCase("BLUE")) {
                            plugin.arenaManager.getArena(player).addPlayerToTeamB(player);
                        }
                    }
                }
            }
        }
    }
}
