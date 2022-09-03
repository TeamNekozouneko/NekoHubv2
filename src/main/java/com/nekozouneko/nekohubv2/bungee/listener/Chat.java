package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Chat implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.getMessage().startsWith("g!")) {
            String content = ChatColor.translateAlternateColorCodes('&', e.getMessage().substring(2));
            ProxiedPlayer by = (ProxiedPlayer) e.getSender();

            String name;

            if (!by.getDisplayName().equals(by.getName())) {
                name = by.getDisplayName();
            } else {
                name = by.getName();
            }

            name += ChatColor.GRAY+" ("+by.getServer().getInfo().getName()+")"+ChatColor.GREEN+": ";
            name += ChatColor.RESET + content;

            instance.getProxy().broadcast(name);
            e.setCancelled(true);
        }
    }

}
