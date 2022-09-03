package com.nekozouneko.nekohubv2.bungee;

import com.nekozouneko.nekohubv2.bungee.cmd.*;
import com.nekozouneko.nekohubv2.bungee.listener.*;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * NekoHub v2 for BungeeCord
 * @author Taitaitatata
 * @see net.md_5.bungee.api.plugin.Plugin
 * @version 1.0
 */
public final class NekoHubv2 extends Plugin {

    /**
     * プラグインインスタンス
     * @see com.nekozouneko.nekohubv2.bungee.NekoHubv2
     */
    public static com.nekozouneko.nekohubv2.bungee.NekoHubv2 instance;

    public String PREFIX = ChatColor.translateAlternateColorCodes('&', "&7[&bNHv2&7] &r");

    public boolean NOW_LOCKED = false;

    public Map<UUID, String> ac_chat = new HashMap<>();

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

    @Override
    public void onEnable() {
        NOW_LOCKED = false;

        getLogger().info(ChatColor.GREEN+"Enabling §fNekoHub v2");

        getLogger().info("Registering commands...");

        getProxy().getPluginManager().registerCommand(this, new Hub("hub", "lobby"));
        getProxy().getPluginManager().registerCommand(this, new Root("bnekohubv2", "bnhv2"));
        getProxy().getPluginManager().registerCommand(this, new Nickname());
        getProxy().getPluginManager().registerCommand(this, new LockProxy());

        getLogger().info("Registering listeners...");
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
        getProxy().getPluginManager().registerListener(this, new PostLogin());
        getProxy().getPluginManager().registerListener(this, new ServerSwitch());
        getProxy().getPluginManager().registerListener(this, new Chat());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnect());
        getProxy().getPluginManager().registerListener(this, new CommandLog());

        getLogger().info("Registering channels...");
        getProxy().registerChannel("nhv2:move");
        getProxy().registerChannel("nhv2:requestserverpanel");
        getProxy().registerChannel("nhv2:openseverpanel");
        getProxy().registerChannel("nhv2:sync");

        getLogger().info("Server data is being sent...");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        try {
            String servers = "";
            for (String K : getProxy().getServers().keySet()) {
                servers += K + ";";
            }

            out.writeUTF(servers);
        } catch (IOException er) {
            er.printStackTrace();
        }

        for (String key : getProxy().getServers().keySet()) {
            getProxy().getServers().get(key).sendData("nhv2:sync", bytes.toByteArray());
        }

        getLogger().info("Sent.");

        getProxy().getScheduler().schedule(this, () -> {
            getLogger().info("Server data is being sent...");

            ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
            DataOutputStream out1 = new DataOutputStream(bytes1);

            try {
                String servers = "";
                for (String K : getProxy().getServers().keySet()) {
                    servers += K + ";";
                }

                out1.writeUTF(servers);
            } catch (IOException er) {
                er.printStackTrace();
            }

            for (String key : getProxy().getServers().keySet()) {
                getProxy().getServers().get(key).sendData("nhv2:sync", bytes1.toByteArray());
            }

            getLogger().info("Sent.");
        }, 600L, 600L, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        NOW_LOCKED = false;
        getLogger().info(ChatColor.RED+"Disabling §fNekoHub v2");

        getLogger().info("Unregistering commands...");
        getProxy().getPluginManager().unregisterCommands(this);

        getLogger().info("Unregistering listeners...");
        getProxy().getPluginManager().unregisterListeners(this);

        getLogger().info("Unregistering channels...");
        getProxy().unregisterChannel("nhv2:move");
        getProxy().unregisterChannel("nhv2:requestserverpanel");
        getProxy().unregisterChannel("nhv2:openseverpanel");
    }

}
