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
        if (e.getMessage().startsWith("!")) {
            String content = ChatCode.toColorCode("&", e.getMessage().substring(1));
            ProxiedPlayer by = (ProxiedPlayer) e.getSender();

            BaseComponent name;

            if (!by.getDisplayName().equals(by.getName())) {
                name = new TextComponent(by.getDisplayName());
            } else {
                name = new TextComponent(by.getName());
            }

            name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity("minecraft:player", by.getUniqueId().toString(), new TextComponent(by.getName()))));
            name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + by.getName() + " "));

            name.addExtra(ChatCode.GRAY+" ("+by.getServer().getInfo().getName()+")"+ChatCode.GREEN+": ");
            name.addExtra(ChatCode.RESET + content);

            instance.getProxy().broadcast(name);
            e.setCancelled(true);
        }
    }

}
