package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

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

        if (e.getTag().equalsIgnoreCase("nhv2:requestserverpanel")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            try {
                ServerSelectorPanelOpenRequestFromBungee(instance.getProxy().getPlayer(in.readUTF()));
            } catch (Exception er) {
                er.printStackTrace();
            }
        }
    }

    /**
     * 鯖選択画面に必要なデータを子鯖に送信
     * @param player 開かせるプレイヤー
     * @throws IOException バイトに書き込むときにバグが発生した用
     */
    private void ServerSelectorPanelOpenRequestFromBungee(ProxiedPlayer player) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        String servers = "";
        for ( ServerInfo val : instance.getProxy().getServers().values() ) {
            if (val.canAccess(player)) {
                servers += val.getName()+":"+val.getMotd().replaceAll("\\n", "\n")+";";
            }
        }

        out.writeUTF(servers);
        out.writeUTF(player.getName());

        player.getServer().sendData("nhv2:openseverpanel", bytes.toByteArray());
    }

}
