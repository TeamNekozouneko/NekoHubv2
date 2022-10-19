package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PluginMessageListener implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();
    private FloodgateApi fa = NekoHubv2.getFInstance();

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
                String un = in.readUTF();
                boolean fjm;
                try {
                    fjm = in.readBoolean();
                } catch (Exception e1) {
                    fjm = false;
                }

                ServerSelectorPanelOpenRequestFromBungee(instance.getProxy().getPlayer(un), fjm);
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
    private void ServerSelectorPanelOpenRequestFromBungee(ProxiedPlayer player, boolean forceJavaMenu) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        String servers = "";
        for ( ServerInfo val : instance.getProxy().getServers().values() ) {
            if (val.canAccess(player)) {
                servers += val.getName() + ":" + val.getMotd().replaceAll("\\n", "\n") + ";";
            }
        }

        FloodgatePlayer fp;
        if (fa != null) {fp = fa.getPlayer(player.getUniqueId());}
        else {fp = null;}
        if (fp != null && !forceJavaMenu && fa != null) {
            SimpleForm.Builder sf = SimpleForm.builder()
                    .title("サーバーを選択...")
                    .content("入りたいサーバーを押すと\nそのサーバーに移動します");

            for (String k : instance.getProxy().getServers().keySet()) {
                sf.button(k);
            }

            sf.button("旧サーバー選択を使う", FormImage.Type.URL, "https://www.nekozouneko.com/assets/arrow-u-left-bottom-bold.png");
            sf.validResultHandler(r -> {
                String l = r.clickedButton().text();
                if (l.equalsIgnoreCase("旧サーバー選択を使う")) {
                    try {
                        ServerSelectorPanelOpenRequestFromBungee(player, true);
                    } catch (IOException ignored) {}
                } else {
                    player.connect(instance.getProxy().getServerInfo(l));
               }
            });
            try {
                fa.sendForm(player.getUniqueId(), sf.build());
            } catch (LinkageError le) {
                instance.getLogger().severe("Linkage Error is called");
                ServerSelectorPanelOpenRequestFromBungee(player, true);
            }
        } else {
            out.writeUTF(servers);
            out.writeUTF(player.getName());

            player.getServer().sendData("nhv2:openseverpanel", bytes.toByteArray());
        }
    }

    private void setSyncEnder(PluginMessageEvent e) {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(instance.getDataFolder(), "aaa.yml"));
    }

}
