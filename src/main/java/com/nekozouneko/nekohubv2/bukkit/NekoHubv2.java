package com.nekozouneko.nekohubv2.bukkit;

import com.nekozouneko.nekohubv2.bukkit.cmd.gserver;
import com.nekozouneko.nekohubv2.bukkit.listener.PluginMessageListener;
import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

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
        getCommand("gserver").setExecutor(new gserver());

        getLogger().info("Registering channels...");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "nhv2:move");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "nhv2:openSeverPanel", new PluginMessageListener());
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatCode.RED+"Disabling §fNekoHub v2");

        // Unregister channels
        getLogger().info("Unregistering channels...");
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
