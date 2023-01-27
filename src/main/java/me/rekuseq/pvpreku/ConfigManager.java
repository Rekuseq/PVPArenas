package me.rekuseq.pvpreku;

import me.rekuseq.pvpreku.Utilities.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.List;
import java.util.Locale;

public class ConfigManager {

    private PVPReku plugin;
    private ConfigurationSection section;
    private ConfigurationSection kitsSection;

    private FileConfiguration arenaConfig;
    private File arenaConfigFile;

    private FileConfiguration config;
    private File configFile;


    public ConfigManager(PVPReku plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        loadArenyConfig();

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }


    public void loadArenyConfig(){
        arenaConfigFile = new File(plugin.getDataFolder(), "areny.yml");
        if (!arenaConfigFile.exists()) {
            plugin.saveResource("areny.yml", true);

        }
        arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
        if (arenaConfig.isConfigurationSection("areny")) {
            section = arenaConfig.getConfigurationSection("areny");
        } else{
            section = arenaConfig.createSection("areny");
        }


        saveArenaConfig();
    }

    public void saveArenaConfig() {
        try {
            arenaConfig.save(arenaConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String path){
        return config.getInt(path);
    }


    /*
    *   ARENY.YML DATAS
    * */

    public Location getArenaLocation(String path){
        return new Location(
                Bukkit.getWorld(section.getString(path+".world")),
                section.getDouble(path+".x"),
                section.getDouble(path+".y"),
                section.getDouble(path+".z")
        );
    }
    public ConfigurationSection getSection(String path){
        return arenaConfig.getConfigurationSection(path);
    }

    public int getRequiredPlayers(String path){
        int teama = section.getInt(path+".team-a-max-players");
        int teamb = section.getInt(path+".team-b-max-players");
        int requiredPlayers = teama + teamb;
        return requiredPlayers;
    }
    public String getString(String path){
        return arenaConfig.getString(path);
    }

    public String getKitName(String path){
        return section.getString(path+".kit");
    }

    public double getDouble(String path){
        return arenaConfig.getDouble(path);
    }

    public int getMaxTeamAPlayers(String path){
        return section.getInt(path+".team-a-max-players");
    }
    public int getMaxTeamBPlayers(String path){
        return section.getInt(path+".team-b-max-players");
    }


    /*
    *  SAVE PLAYERS INVENTORY
    * */

    public void savePlayerInventory(Player p) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath()+"/playerdata", p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory.armor", p.getInventory().getArmorContents());
        c.set("inventory.content", p.getInventory().getContents());
        c.save(f);
    }


    @SuppressWarnings("unchecked")
    public void restorePlayerInventory(Player p) {
        File f = new File(plugin.getDataFolder().getAbsolutePath()+"/playerdata", p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
        f.delete();
    }

    /*
     *  SAVE/RESTORE KITS INVENTORY
     * */

    public void saveKitsInventory(Player p, String name) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath()+"/kits", name + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory.armor", p.getInventory().getArmorContents());
        c.set("inventory.content", p.getInventory().getContents());
        c.save(f);
        p.getInventory().clear();
    }

    public void deleteSavedKit(String name){
        File f = new File(plugin.getDataFolder().getAbsolutePath()+"/kits", name + ".yml");
        if(f.exists()){
            f.delete();
        }
    }


    @SuppressWarnings("unchecked")
    public void restoreKitsInventory(Player p, String name) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath()+"/kits", name + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
    }
}

