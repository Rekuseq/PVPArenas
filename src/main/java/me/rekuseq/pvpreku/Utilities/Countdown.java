package me.rekuseq.pvpreku.Utilities;

import me.rekuseq.pvpreku.ENUMS.GameState;
import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown{
    private final PVPReku plugin;
    private final Arena arena;
    private int countdownSeconds;

    public Countdown(PVPReku plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;
        this.countdownSeconds = plugin.configManager.getInt("countdown");
    }

    public void reset(){
        arena.setState(GameState.RECRUITING);
        this.countdownSeconds = plugin.configManager.getInt("countdown");
    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(arena.getPlayers().size() < plugin.configManager.getRequiredPlayers(arena.getName())){
                    reset();
                    cancel();
                    return;
                }

                if(countdownSeconds == 0){
                    cancel();
                    arena.sendMessage("START");
                    arena.start();
                    return;
                }

                if(countdownSeconds <=10 || countdownSeconds % 15 == 0){
                    arena.sendMessage("&aGra wystartuje za " + countdownSeconds + " sekund" + (countdownSeconds <= 4 && countdownSeconds >1 ? "y" : "")+ (countdownSeconds == 1 ? "e" : "") + ".");
                }
                countdownSeconds--;
            }
        }.runTaskTimer(plugin, 0,20);
    }
}