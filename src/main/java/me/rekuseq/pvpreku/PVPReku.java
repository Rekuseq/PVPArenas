package me.rekuseq.pvpreku;

import me.rekuseq.pvpreku.Commands.PVPArenasCommands;
import me.rekuseq.pvpreku.Listeners.BlockBreakListener;
import me.rekuseq.pvpreku.Listeners.PlayerDeathListener;
import me.rekuseq.pvpreku.Listeners.PlayerInterractListener;
import me.rekuseq.pvpreku.Utilities.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public final class PVPReku extends JavaPlugin implements Listener {

    public ArenaManager arenaManager;
    public final ConfigManager configManager;
    private final PVPArenasCommands pvpArenasCommands;

    public PVPReku(){
        this.configManager = new ConfigManager(this);
        this.arenaManager = new ArenaManager(this);
        this.pvpArenasCommands = new PVPArenasCommands(this);
    }

    @Override
    public void onEnable() {
        getCommand("pvp").setExecutor(pvpArenasCommands);
        registerEvents();

        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                var item = new ItemStack(Material.LEATHER_HELMET);
                var item2 = new ItemStack(Material.STONE);
                var item3 = new ItemStack(Material.BEACON);
                var itemm = item.getItemMeta();
                var itemm2 = item2.getItemMeta();
                var itemm3 = item3.getItemMeta();

                itemm.setDisplayName("test");
                itemm.addEnchant(Enchantment.ARROW_FIRE, 1, false);

                item2.setAmount(20);
                item2.setLore(Arrays.stream("lore1 lore2".split(" ")).toList());

                itemm3.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
                itemm3.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                item.setItemMeta(itemm);
                item2.setItemMeta(itemm2);
                item3.setItemMeta(itemm3);


            }
        },100);

    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInterractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);

    }


    public static String convertItemStackToString(ItemStack item){
        try {
            // org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream io = new org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream();
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(item);
            os.flush();
            return new String(Base64.getEncoder().encode(io.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static ItemStack convertStringToItemStack(String data){
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    @EventHandler
    public void join(PlayerJoinEvent e){
        PlayerInventory inv = e.getPlayer().getInventory();
        var item = new ItemStack(Material.LEATHER_HELMET);
        var item2 = new ItemStack(Material.STONE);
        var item3 = new ItemStack(Material.BEACON);
        var itemm = item.getItemMeta();
        var itemm2 = item2.getItemMeta();
        var itemm3 = item3.getItemMeta();

        itemm.setDisplayName("test");
        itemm.addEnchant(Enchantment.ARROW_FIRE, 1, false);

        item2.setAmount(20);
        item2.setLore(Arrays.stream("lore1 lore2".split(" ")).toList());

        itemm3.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        itemm3.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(itemm);
        item2.setItemMeta(itemm2);
        item3.setItemMeta(itemm3);
        inv.clear();

        inv.addItem(item);

        inv.addItem(item2);

        inv.addItem(item3);

        var s = convertItemStackToString(item);
        var s2 = convertItemStackToString(item2);
        var s3 = convertItemStackToString(item3);

        System.out.println(s);
        System.out.println(s2);
        System.out.println(s3);

        inv.addItem(convertStringToItemStack(s));

        inv.addItem(convertStringToItemStack(s2));

        inv.addItem(convertStringToItemStack(s3));

    }

}
