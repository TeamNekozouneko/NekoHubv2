package com.nekozouneko.nekohubv2.bungee.cmd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Nickname extends Command {

    public Nickname() {
        super("bnickname", "NekoHubv2.cmd.nickname", "bnick");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String nick;

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "プレイヤーとしてのみ実行可能です。"));
            return;
        } else if (args.length >= 1) {
            nick = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "引数を定義してください"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        player.setDisplayName(nick);
    }
}
