package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnect implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e) {
        if (instance.NOW_LOCKED) return;

        instance.getProxy().broadcast(new TextComponent(ChatColor.GRAY+e.getPlayer().getName()+"が退出しました"));
    }

}
