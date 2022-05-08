package com.nekozouneko.nekohubv2.bungee;

import com.nekozouneko.nekohubv2.bungee.cmd.*;
import com.nekozouneko.nekohubv2.bungee.listener.*;

import com.nekozouneko.nplib.chat.ChatCode;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class NekoHubv2 extends Plugin {

    /**
     * プラグインインスタンス
     * @see com.nekozouneko.nekohubv2.bungee.NekoHubv2
     */
    public static com.nekozouneko.nekohubv2.bungee.NekoHubv2 instance;

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
        getLogger().info(ChatCode.GREEN+"Enabling §fNekoHub v2");

        getLogger().info("Registering commands...");

        getProxy().getPluginManager().registerCommand(this, new Hub("hub", "lobby"));

        getLogger().info("Registering listeners...");
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
        getProxy().getPluginManager().registerListener(this, new PostLogin());
        getProxy().getPluginManager().registerListener(this, new ServerSwitch());
        getProxy().getPluginManager().registerListener(this, new Chat());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnect());

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
        getLogger().info(ChatCode.RED+"Disabling §fNekoHub v2");

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
