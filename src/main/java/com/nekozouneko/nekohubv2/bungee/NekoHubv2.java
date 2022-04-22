package com.nekozouneko.nekohubv2.bungee;

import com.nekozouneko.nekohubv2.bungee.cmd.*;
import com.nekozouneko.nekohubv2.bungee.listener.*;

import com.nekozouneko.nplib.chat.ChatCode;

import net.md_5.bungee.api.plugin.Plugin;

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
    }

}
