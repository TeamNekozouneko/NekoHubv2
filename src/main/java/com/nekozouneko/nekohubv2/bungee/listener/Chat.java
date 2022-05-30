package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Chat implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.getMessage().startsWith("g!")) {
            String content = ChatCode.toColorCode("&", e.getMessage().substring(2));
            ProxiedPlayer by = (ProxiedPlayer) e.getSender();

            String name;

            if (!by.getDisplayName().equals(by.getName())) {
                name = by.getDisplayName();
            } else {
                name = by.getName();
            }

            name += ChatCode.GRAY+" ("+by.getServer().getInfo().getName()+")"+ChatCode.GREEN+": ";
            name += ChatCode.RESET + content;

            instance.getProxy().broadcast(name);
            e.setCancelled(true);
        }
    }

}
