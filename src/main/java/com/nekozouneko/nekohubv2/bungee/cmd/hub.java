package com.nekozouneko.nekohubv2.bungee.cmd;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Hub extends Command {

    private final NekoHubv2 instance = NekoHubv2.getInstance();

    public Hub(String cmdName, String... aliases) {
        super(cmdName, null, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        ListenerInfo listener = (ListenerInfo) instance.getProxy().getConfig().getListeners().toArray()[0];
        ServerInfo ConnectableServ = instance.getProxy().getServerInfo(listener.getServerPriority().get(0));

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (ConnectableServ == null) {
            sender.sendMessage(new TextComponent(ChatCode.RED+"ロビーサーバーがオフラインの可能性があります。"));
            return;
        }
        if (player == null) {
            sender.sendMessage(new TextComponent(ChatCode.RED+"プレイヤーとしてのみ実行可能です。"));
        }

        player.connect(ConnectableServ);

    }
}
