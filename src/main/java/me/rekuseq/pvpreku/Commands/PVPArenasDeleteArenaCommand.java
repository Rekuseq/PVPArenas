package me.rekuseq.pvpreku.Commands;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PVPArenasDeleteArenaCommand extends SubCommand{

    private PVPReku plugin;
    public PVPArenasDeleteArenaCommand(PVPReku plugin) {
        super(true, 1, "delete", "pvp.deletearena", plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) throws IOException {
        if (sender instanceof Player){
            Player p = (Player) sender;
        }

        return false;
    }

    @Override
    public String getSyntax() {
        return null;
    }
}
