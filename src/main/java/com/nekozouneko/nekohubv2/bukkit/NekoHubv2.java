package com.nekozouneko.nekohubv2.bukkit;

import com.nekozouneko.nekohubv2.bukkit.cmd.*;
import com.nekozouneko.nekohubv2.bukkit.listener.*;

import com.nekozouneko.nplib.chat.ChatCode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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

    public String PREFIX = ChatCode.toColorCode("&", "&7[&bNHv2&7] &r");

    public boolean linked = false;

    public List<String> servers = new ArrayList<>();

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
        getLogger().info(ChatCode.GREEN+"Enabling §fNekoHub v2");

        // Register commands / channels
        getLogger().info("Registering commands...");
        getCommand("move").setExecutor(new Move());
        getCommand("serverpanel").setExecutor(new ServerPanel());
        getCommand("stickmenu").setExecutor(new StickMenu());
        getCommand("stick").setExecutor(new Stick());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("rule").setExecutor(new Rule());
        getCommand("nekohubv2").setExecutor(new Root());

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

        getConfig().addDefault("menu.buttons.trash_box", true);

        getConfig().addDefault("donate.fly", false);
    }

    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {
        getLogger().info(ChatCode.RED+"Disabling §fNekoHub v2");

        // Unregister channels
        getLogger().info("Unregistering channels...");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
