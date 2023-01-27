package me.rekuseq.pvpreku.Commands;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PVPArenasCreateArenaCommand extends SubCommand{

    private PVPReku plugin;
    public PVPArenasCreateArenaCommand(PVPReku plugin) {
        super(true, 1 ,"create", "pvp.admin.createarena", plugin);
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
