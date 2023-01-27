package me.rekuseq.pvpreku.Utilities;

import me.rekuseq.pvpreku.ENUMS.GameState;
import me.rekuseq.pvpreku.Game;
import me.rekuseq.pvpreku.PVPReku;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private final PVPReku plugin;

    private String name;
    private Location lobbyAreny;
    private Location spawnTeamA;
    private Location spawnTeamB;

    private GameState state;
    private List<UUID> players;
    private List<UUID> teamA;
    private List<UUID> teamB;
    private Countdown countdown;
    private Game game;


    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack pants;
    private ItemStack boots;

    public Arena(PVPReku plugin, String name, Location lobbyAreny, Location spawnTeamA, Location spawnTeamB) {
        this.plugin = plugin;
        this.name = name;
        this.lobbyAreny = lobbyAreny;
        this.spawnTeamA = spawnTeamA;
        this.spawnTeamB = spawnTeamB;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.teamA = new ArrayList<>();
        this.teamB = new ArrayList<>();
        this.countdown = new Countdown(plugin, this);
        this.game = new Game(this, plugin);


        this.helmet = new ItemStack(Material.LEATHER_HELMET);
        this.chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        this.pants = new ItemStack(Material.LEATHER_LEGGINGS);
        this.boots = new ItemStack(Material.LEATHER_BOOTS);
    }

    public void start() {
        for (UUID uuid : teamA) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(spawnTeamA);
        }
        for (UUID uuid : teamB) {
            Bukkit.getPlayer(uuid).teleport(spawnTeamB);
        }
        game.start();
    }

    public void reset() throws IOException {
        game.reset();
    }

    public void addPlayerToTeamA(Player player) {
        if (players.contains(player.getUniqueId())) {
            if (plugin.configManager.getMaxTeamAPlayers(name) != teamA.size()) {
                teamA.add(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Jesteś w drużynie &cCZERWONYCH&7!"));

                setPlayerTeamArmor(player, Color.RED);
            }
        } else if (teamB.contains(player.getUniqueId())) {
            if (plugin.configManager.getMaxTeamAPlayers(name) != teamA.size()) {
                removePlayerFromTeamB(player);
                teamA.add(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Jesteś w drużynie &cCZERWONYCH&7!"));
                setPlayerTeamArmor(player, Color.RED);
            }
        }
    }

    public void setPlayerTeamArmor(Player player, Color color) {
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        LeatherArmorMeta pantsMeta = (LeatherArmorMeta) pants.getItemMeta();
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

        helmetMeta.setColor(color);
        chestplateMeta.setColor(color);
        pantsMeta.setColor(color);
        bootsMeta.setColor(color);

        helmet.setItemMeta(helmetMeta);
        chestplate.setItemMeta(chestplateMeta);
        pants.setItemMeta(pantsMeta);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(pants);
        player.getInventory().setBoots(boots);
    }

    public void addPlayerToTeamB(Player player) {
        if (players.contains(player.getUniqueId())) {
            if (plugin.configManager.getMaxTeamBPlayers(name) >= teamB.size()) {
                teamB.add(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Jesteś w drużynie &bNIEBIESKICH&7!"));
                setPlayerTeamArmor(player, Color.BLUE);
            }
        } else if (teamA.contains(player.getUniqueId())) {
            if (plugin.configManager.getMaxTeamBPlayers(name) >= teamB.size()) {
                removePlayerFromTeamA(player);
                teamB.add(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Jesteś w drużynie &bNIEBIESKICH&7!"));
                setPlayerTeamArmor(player, Color.BLUE);
            }
        }
    }

    public void removePlayerFromTeamA(Player player) {
        if (teamA.contains(player.getUniqueId())) {
            teamA.remove(player.getUniqueId());
        }
    }

    public void removePlayerFromTeamB(Player player) {
        if (teamB.contains(player.getUniqueId())) {
            teamB.remove(player.getUniqueId());
        }
    }


    public void addPlayer(Player player) throws IOException {
        players.add(player.getUniqueId());
        plugin.configManager.savePlayerInventory(player);
        player.getInventory().clear();
        player.teleport(lobbyAreny);

        if (state.equals(GameState.RECRUITING) && players.size() >= plugin.configManager.getRequiredPlayers(name)) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) throws IOException {
        if (isPlayerInArena(player)) {
            players.remove(player.getUniqueId());
            player.getInventory().clear();
            plugin.configManager.restorePlayerInventory(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOpuszczasz arene"));
        }
    }

    public boolean isPlayerInArena(Player player) {
        if (players.contains(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public void sendMessage(String msg) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public String getName() {
        return name;
    }

    public List<UUID> getPlayers() {
        return players;
    }


    public boolean isPlayerInTeamA(Player player) {
        if (teamA.contains(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isPlayerInTeamB(Player player) {
        if (teamB.contains(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }


    public void setState(GameState state) {
        this.state = state;
    }


}
