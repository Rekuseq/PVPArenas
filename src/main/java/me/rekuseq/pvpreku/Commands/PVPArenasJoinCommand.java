package me.rekuseq.pvpreku.Commands;

import me.rekuseq.pvpreku.PVPReku;
import me.rekuseq.pvpreku.Utilities.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PVPArenasJoinCommand extends SubCommand{

    private PVPReku plugin;
    public PVPArenasJoinCommand(PVPReku plugin) {
        super(true, 2, "join", "pvp.joinarena", plugin);
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, String[] args) throws IOException {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 2){
                if(plugin.arenaManager.isPlayerNotInAnyArenas(p)){
                    for(Arena arena : plugin.arenaManager.getArenas()){
                        if(args[1].equalsIgnoreCase(arena.getName())){
                            plugin.arenaManager.getArena(args[1]).addPlayer(p);
                        }
                    }
                } else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cJestes juz na arenie!"));
                }
            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('7',"&7Uzyj komendy &c/pvp join <nazwa areny> &7 aby dolaczyc"));
            }
        }

        return false;
    }

    @Override
    public String getSyntax() {
        return null;
    }
}
