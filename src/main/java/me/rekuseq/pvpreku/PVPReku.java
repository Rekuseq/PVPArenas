package me.rekuseq.pvpreku;

import me.rekuseq.pvpreku.Commands.PVPArenasCommands;
import me.rekuseq.pvpreku.Listeners.BlockBreakListener;
import me.rekuseq.pvpreku.Listeners.PlayerDeathListener;
import me.rekuseq.pvpreku.Listeners.PlayerInterractListener;
import me.rekuseq.pvpreku.Utilities.ArenaManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PVPReku extends JavaPlugin {

    public ArenaManager arenaManager;
    public final ConfigManager configManager;
    private PVPArenasCommands pvpArenasCommands;

    public PVPReku(){
        this.configManager = new ConfigManager(this);
        this.arenaManager = new ArenaManager(this);
        this.pvpArenasCommands = new PVPArenasCommands(this);
    }

    @Override
    public void onEnable() {
        getCommand("pvp").setExecutor(pvpArenasCommands);
        registerEvents();
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInterractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

}
