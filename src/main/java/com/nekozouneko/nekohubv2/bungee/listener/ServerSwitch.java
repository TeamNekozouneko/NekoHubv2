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
        BaseComponent connected;
        if (e.getPlayer().getDisplayName().equals(e.getPlayer().getName())) {
            connected = new TextComponent(ChatCode.GRAY + e.getPlayer().getName());
        } else {
            connected = new TextComponent(ChatCode.GRAY + e.getPlayer().getName() + " ("+e.getPlayer().getDisplayName()+")");
        }

        connected.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity("minecraft:player", e.getPlayer().getUniqueId().toString(), new TextComponent(e.getPlayer().getName()))));
        connected.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+e.getPlayer().getName() + " "));

        BaseComponent moveMessage = new TextComponent();

        moveMessage.addExtra(connected);
        moveMessage.addExtra(ChatCode.GRAY+"が"+e.getPlayer().getServer().getInfo().getName()+"に移動しました");

        instance.getProxy().broadcast(moveMessage);
    }

}
