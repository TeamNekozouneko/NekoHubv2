package com.nekozouneko.nekohubv2.bukkit;

import com.nekozouneko.nekohubv2.bukkit.cmd.Move;
import com.nekozouneko.nekohubv2.bukkit.cmd.ServerPanel;
import com.nekozouneko.nekohubv2.bukkit.cmd.Stick;
import com.nekozouneko.nekohubv2.bukkit.cmd.StickMenu;
import com.nekozouneko.nekohubv2.bukkit.listener.*;

import com.nekozouneko.nplib.chat.ChatCode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * NekoHub v2 for bukkit / Spigot
 * @author Taitaitatata
 * @version 1.0
 */
public final class NekoHubv2 extends JavaPlugin {

    /**
     * プラグインのインスタンスです。
     * @see com.nekozouneko.nekohubv2.bukkit.NekoHubv2
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    public static NekoHubv2 instance;

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

        getLogger().info("Registering channels...");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "nhv2:move");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "nhv2:requestserverpanel");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "nhv2:openseverpanel", new PluginMessageListener());

        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new InventoryAction(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
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
