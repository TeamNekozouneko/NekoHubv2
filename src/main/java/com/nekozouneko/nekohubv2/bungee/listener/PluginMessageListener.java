package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PluginMessageListener implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (e.isCancelled() || !e.getTag().startsWith("nhv2:")) return;

        if (e.getTag().equalsIgnoreCase("nhv2:move")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            try {
                ProxiedPlayer player = instance.getProxy().getPlayer(in.readUTF());
                ServerInfo server = instance.getProxy().getServerInfo(in.readUTF());

                if (server == null) return;

                player.connect(server);
            } catch (Exception er) {
                er.printStackTrace();
            }
        }
    }

}
