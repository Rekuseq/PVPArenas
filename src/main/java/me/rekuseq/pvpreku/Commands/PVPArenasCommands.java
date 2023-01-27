package me.rekuseq.pvpreku.Commands;

import lombok.SneakyThrows;
import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PVPArenasCommands implements CommandExecutor {


    private PVPReku plugin;
    public static List<SubCommand> commands = new ArrayList<>();

    public PVPArenasCommands(PVPReku plugin) {
        this.plugin = plugin;

        commands.add(new PVPArenasJoinCommand(plugin));
        commands.add(new PVPArenasLeaveCommand(plugin));
        commands.add(new PVPArenasCreateArenaCommand(plugin));
        commands.add(new PVPArenasDeleteArenaCommand(plugin));
        commands.add(new PVPKitManagment(plugin));
    }

    @SneakyThrows
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("ssd");
            } else {
                for (SubCommand subCommand : commands) {
                    if (subCommand.getName().equalsIgnoreCase(args[0])) {
                        if (args.length >= subCommand.getMinArgs()) {
                            subCommand.onCommand(sender, args);
                        } else {
                            player.sendMessage(ChatColor.RED + "[ERROR] => Nie ma takiej komendy!");
                        }
                    }
                }
            }
        }


        return false;
    }
}
