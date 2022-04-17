package com.nekozouneko.nekohubv2.bukkit;

import com.nekozouneko.nekohubv2.bukkit.cmd.GServer;
import com.nekozouneko.nekohubv2.bukkit.cmd.ServerPanel;
import com.nekozouneko.nekohubv2.bukkit.cmd.Stick;
import com.nekozouneko.nekohubv2.bukkit.cmd.StickMenu;
import com.nekozouneko.nekohubv2.bukkit.listener.InventoryAction;
import com.nekozouneko.nekohubv2.bukkit.listener.PlayerInteract;
import com.nekozouneko.nekohubv2.bukkit.listener.PluginMessageListener;

import com.nekozouneko.nplib.chat.ChatCode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NekoHubv2 extends JavaPlugin {

    public static NekoHubv2 instance;

    public static NekoHubv2 getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getLogger().info(ChatCode.GREEN+"Enabling §fNekoHub v2");

        // Register commands / channels
        getLogger().info("Registering commands...");
        getCommand("gserver").setExecutor(new GServer());
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
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatCode.RED+"Disabling §fNekoHub v2");

        // Unregister channels
        getLogger().info("Unregistering channels...");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
