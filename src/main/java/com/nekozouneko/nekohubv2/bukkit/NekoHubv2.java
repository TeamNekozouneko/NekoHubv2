package com.nekozouneko.nekohubv2.bukkit;

import com.nekozouneko.nekohubv2.bukkit.cmd.*;
import com.nekozouneko.nekohubv2.bukkit.listener.*;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * NekoHub v2 for bukkit / Spigot
 * @author Taitaitatata
 * @see org.bukkit.plugin.java.JavaPlugin
 * @version 1.0
 */
public final class NekoHubv2 extends JavaPlugin {

    /**
     * プラグインのインスタンスです。
     * @see com.nekozouneko.nekohubv2.bukkit.NekoHubv2
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    public static NekoHubv2 instance;
    public String PREFIX = ChatColor.translateAlternateColorCodes('&', "&7[&bNHv2&7] &r");
    public boolean linked = false;
    public String defaultWorld = "world";
    public List<String> servers = new ArrayList<>();
    public static LuckPerms luckPerms = null;

    /**
     * プラグインのインスタンスを取得します。
     * @return プラグインのインスタンス
     */
    public static NekoHubv2 getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN+"Enabling §fNekoHub v2");

        // Register commands / channels
        getLogger().info("Registering commands...");
        getCommand("move").setExecutor(new Move());
        getCommand("serverpanel").setExecutor(new ServerPanel());
        getCommand("stickmenu").setExecutor(new StickMenu());
        getCommand("stick").setExecutor(new Stick());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("rule").setExecutor(new Rule());
        getCommand("nekohubv2").setExecutor(new Root());
        getCommand("as").setExecutor(new As());
        getCommand("spawn").setExecutor(new Spawn());
        if (setupLuckPerms()) {
            getCommand("prefix").setExecutor(new Prefix());
        }

        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new InventoryAction(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        getLogger().info("Registering channels...");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "nhv2:move");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "nhv2:requestserverpanel");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "nhv2:openseverpanel", new PluginMessageListener());
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "nhv2:sync", new PluginMessageListener());

        getLogger().info("Loading configurations...");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getConfig().addDefault("rule", "&a&lマイクラ鯖共通ルール\n" +
                "&f・道のど真ん中に建築はしないでください。\n" +
                "&f・荒らし・チート・ハッキングなどは禁止します。\n" +
                "&f・ハッククライアントは使用しないでください。\n" +
                "&f・人の家(敷地)に勝手に入らないでください\n" +
                "&f・人を &n'わざと'&f 倒し、アイテムを略奪をすることを禁止します。\n" +
                "&f・&c&lOPの指示に従ってください。\n" +
                "&f・&cVPN/プロキシを&n経由&cしてアクセスしないでください");

        getConfig().addDefault("menu.buttons.server_selector", true);
        getConfig().addDefault("menu.buttons.last_spawn", true);
        getConfig().addDefault("menu.buttons.ender_chest", true);
        getConfig().addDefault("menu.buttons.player_head", true);
        getConfig().addDefault("menu.buttons.first_spawn", true);
        getConfig().addDefault("menu.buttons.suicide", true);

        getConfig().addDefault("menu.buttons.trash_box", true);

        getConfig().addDefault("donate.fly", false);

        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(Paths.get("server.properties")));

            defaultWorld = p.getProperty("level-name", "world");
        } catch (IOException ignored) {}

    }

    private boolean setupLuckPerms() {
        if (getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
            luckPerms = LuckPermsProvider.get();
            return true;
        } else {
            return false;
        }
    }

    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED+"Disabling §fNekoHub v2");

        // Unregister channels
        getLogger().info("Unregistering channels...");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
    }

}
