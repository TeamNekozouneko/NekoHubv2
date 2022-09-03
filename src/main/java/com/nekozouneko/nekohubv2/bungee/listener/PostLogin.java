package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.*;

public class PostLogin implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPlayerConnected(PostLoginEvent e) {
        if (instance.NOW_LOCKED) return;
        String connected;
        if (e.getPlayer().getDisplayName().equals(e.getPlayer().getName())) {
            connected = ChatColor.GRAY + e.getPlayer().getName();
        } else {
            connected = ChatColor.GRAY + e.getPlayer().getName() + " ("+e.getPlayer().getDisplayName()+") ";
        }

        String joinMessage = "";

        joinMessage += connected;
        joinMessage += ChatColor.GRAY+"が参加しました";

        instance.getProxy().broadcast(new TextComponent(joinMessage));
    }

    @EventHandler
    public void onLoginReq(PreLoginEvent e) {
        if (instance.NOW_LOCKED) {
            instance.getLogger().info("Denied access by " + e.getConnection().getName() + " (" + e.getConnection().getSocketAddress() + ")");

            e.setCancelReason(new TextComponent(ChatColor.RED + (ChatColor.UNDERLINE + "注意\n\n") + ChatColor.RESET + "現在荒らしやボットアタックの被害を受けているため\n新規ログインができなくなっています。\nしばらくお待ちの上再ログインしてください。"));
            e.setCancelled(true);
        }
    }

}
