package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.*;

public class PostLogin implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPlayerConnected(PostLoginEvent e) {
        String connected;
        if (e.getPlayer().getDisplayName().equals(e.getPlayer().getName())) {
            connected = ChatCode.GRAY + e.getPlayer().getName();
        } else {
            connected = ChatCode.GRAY + e.getPlayer().getName() + " ("+e.getPlayer().getDisplayName()+") ";
        }

        String joinMessage = "";

        joinMessage += connected;
        joinMessage += ChatCode.GRAY+"が参加しました";

        instance.getProxy().broadcast(new TextComponent(joinMessage));
    }

}
