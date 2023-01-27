package me.rekuseq.pvpreku.Commands;

import lombok.Getter;
import lombok.Setter;
import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.command.CommandSender;

import java.io.IOException;


public abstract class SubCommand {

    @Setter
    @Getter
    protected boolean onlyPlayers;

    public SubCommand(boolean onlyPlayers, int minArgs, String name, String permission, PVPReku plugin) {
        this.onlyPlayers = onlyPlayers;
        this.minArgs = minArgs;
        this.name = name;
        this.permission = permission;
        this.plugin = plugin;
    }

    @Setter
    @Getter
    protected int minArgs;

    @Getter
    @Setter
    protected String name, permission;

    @Setter
    @Getter
    protected PVPReku plugin;

    public abstract boolean onCommand(CommandSender sender, String[] args) throws IOException;

    public abstract String getSyntax();

}
