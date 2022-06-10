package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandLog implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    public CommandLog() {
        
    }

    @EventHandler
    public void onCommand(ChatEvent e) {
        if (e.isProxyCommand()) {

        }
    }

}
