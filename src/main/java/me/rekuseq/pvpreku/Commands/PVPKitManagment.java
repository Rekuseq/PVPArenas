package me.rekuseq.pvpreku.Commands;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PVPKitManagment extends SubCommand{

    private PVPReku plugin;
    public PVPKitManagment(PVPReku plugin) {
        super(true, 3, "kit", "pvp.joinarena", plugin);
        this.plugin = plugin;
    }
    private String usage = ChatColor.translateAlternateColorCodes('&',"&7Uzycie komendy: &c/pvp kit create <nazwa>");

    @Override
    public boolean onCommand(CommandSender sender, String[] args) throws IOException {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 3){
                if(args[1].equalsIgnoreCase("create")){
                    plugin.configManager.saveKitsInventory(player, args[2]);
                } else if(args[1].equalsIgnoreCase("delete")){
                    plugin.configManager.deleteSavedKit(args[2]);
                } else{
                    player.sendMessage(usage);
                }
            } else{
                player.sendMessage(usage);
            }

        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cKomenda wylacznie dla graczy!"));
        }
        return false;
    }

    @Override
    public String getSyntax() {
        return null;
    }
}
