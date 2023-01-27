package me.rekuseq.pvpreku.Utilities;

import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private final PVPReku plugin;

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(PVPReku plugin){
        this.plugin = plugin;

        for(String str : plugin.configManager.getSection("areny").getKeys(false)){
            arenas.add(new Arena(plugin, str,
                    plugin.configManager.getArenaLocation(str+".lobby-areny"),
                    plugin.configManager.getArenaLocation(str+".spawn-areny-team-a"),
                    plugin.configManager.getArenaLocation(str+".spawn-areny-team-b")));
        }
    }

    public void addArena(Arena arena){
        arenas.add(arena);
    }

    public List<Arena> getArenas(){
        return arenas;
    }

    public Arena getArena(String name){
        for(Arena arena : arenas){
            if(arena.getName().equalsIgnoreCase(name)){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(Player player){
        for(Arena arena : arenas){
            if(arena.isPlayerInArena(player)){
                return arena;
            }
        }
        return null;
    }

    public boolean isPlayerNotInAnyArenas(Player player){
        for (Arena arena : arenas){
            if(!arena.isPlayerInArena(player)){
                return true;
            } else{
                return false;
            }
        }
        return false;
    }

}
