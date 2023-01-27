package me.rekuseq.pvpreku.Commands;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PVPArenasLeaveCommand extends SubCommand{

    private PVPReku plugin;
    public PVPArenasLeaveCommand(PVPReku plugin) {
        super(true, 1, "leave", "pvp.joinarena", plugin);
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, String[] args) throws IOException {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!plugin.arenaManager.isPlayerNotInAnyArenas(p)){
                if(plugin.arenaManager.getArena(p).isPlayerInTeamA(p)){
                    plugin.arenaManager.getArena(p).removePlayerFromTeamA(p);
                    plugin.arenaManager.getArena(p).removePlayer(p);
                } else if(plugin.arenaManager.getArena(p).isPlayerInTeamB(p)){
                    plugin.arenaManager.getArena(p).removePlayerFromTeamB(p);
                    plugin.arenaManager.getArena(p).removePlayer(p);
                } else{
                    plugin.arenaManager.getArena(p).removePlayer(p);
                }
            } else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Nie jestes na zadnej arenie!"));
            }
        }

        return false;
    }

    @Override
    public String getSyntax() {
        return null;
    }
}
