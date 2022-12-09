package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import com.nekozouneko.nutilsxlib.chat.NChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;

import java.io.*;

public class PluginMessageListener implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();
    private GeyserApi ga = NekoHubv2.getGInstance();

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (e.isCancelled() || !e.getTag().startsWith("nhv2:")) return;

        // @Deprecated
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
                servers += val.getName() + ":" + val.getMotd().replace("\\n", "\n") +  ":" + val.getPlayers().size() + ";";
            }
        }

        GeyserConnection gc;
        if (ga != null) gc = ga.connectionByUuid(player.getUniqueId());
        else gc = null;
        if (gc != null && !forceJavaMenu && ga != null) {
            SimpleForm.Builder sf = SimpleForm.builder()
                    .title("サーバーを選択... (Select a server...)")
                    .content("入りたいサーバーを押すと\nそのサーバーに移動します\n(Click to join)");

            for (String k : instance.getProxy().getServers().keySet()) {
                if (instance.getProxy().getServerInfo(k).canAccess(player)) {
                    sf.button(k + "\n" + NChatColor.replaceAltColorCodes(instance.getProxy().getServers().get(k).getMotd()));
                }
            }

            sf.button("旧サーバー選択を使う", FormImage.Type.URL, "https://www.nekozouneko.com/assets/arrow-u-left-bottom-bold.png");
            sf.validResultHandler(r -> {
                String l = r.clickedButton().text();
                if (l.equalsIgnoreCase("旧サーバー選択を使う")) {
                    try {
                        ServerSelectorPanelOpenRequestFromBungee(player, true);
                    } catch (IOException ignored) {}
                } else {
                    player.connect(instance.getProxy().getServerInfo(l.split("\n")[0]));
               }
            });
            try {
                ga.sendForm(player.getUniqueId(), sf.build());
            } catch (LinkageError le) {
                instance.getLogger().severe("Linkage Error is called");
                ServerSelectorPanelOpenRequestFromBungee(player, true);
            } catch (Exception e) {
                e.printStackTrace();
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
