package me.rekuseq.pvpreku;

import me.rekuseq.pvpreku.ENUMS.GameState;
import me.rekuseq.pvpreku.Utilities.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.UUID;

public class Game extends BukkitRunnable {
    private Arena arena;
    private PVPReku plugin;

    public Game(Arena arena, PVPReku plugin){
        this.arena = arena;
        this.plugin = plugin;
    }


    public void start(){
        arena.setState(GameState.LIVE);
        //Dodanie kitow
        try {
            for(UUID uuid : arena.getPlayers()){
                Player p = Bukkit.getPlayer(uuid);
                plugin.configManager.restoreKitsInventory(p, plugin.configManager.getKitName(arena.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {

    }

    public void reset() throws IOException {

        start();
    }

    public void stop(){

    }
}
