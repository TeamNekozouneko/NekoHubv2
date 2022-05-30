package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitch implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        String connected;
        if (e.getPlayer().getDisplayName().equals(e.getPlayer().getName())) {
            connected = ChatCode.GRAY + e.getPlayer().getName();
        } else {
            connected = ChatCode.GRAY + e.getPlayer().getName() + " ("+e.getPlayer().getDisplayName()+")";
        }

        String moveMessage = "";

        moveMessage += connected;
        moveMessage += ChatCode.GRAY+"が"+e.getPlayer().getServer().getInfo().getName()+"に移動しました";

        instance.getProxy().broadcast(moveMessage);
    }

}
