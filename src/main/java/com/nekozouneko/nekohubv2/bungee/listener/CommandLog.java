package com.nekozouneko.nekohubv2.bungee.listener;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.AsyncEvent;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandLog implements Listener {

    private final String[] IGNORE_LOGGING = new String[]{"/tell", "/w", "/reply", "/msg", "/m", "/t"};

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onCommand(ChatEvent e) {
        if (!(e.isProxyCommand() && e.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        String content = e.getMessage();
        for (String arg : e.getMessage().split(" ")) {
            if (isIp(arg)) {
                content = content.replace(arg, "**.**.**.**");
            }
        }

        logger:for (ProxiedPlayer player : instance.getProxy().getPlayers()) {
            if (p.hasPermission("nekohubv2.log.showlog")) {
                String[] args = e.getMessage().split(" ");

                if (e.getMessage().startsWith("/as") || e.getMessage().startsWith("/sudo") ||
                        e.getMessage().startsWith("/nekohubv2:as") || e.getMessage().startsWith("/nekohubv2:sudo") &&
                        args.length >= 3
                ) {
                    p.sendMessage(new TextComponent(
                            instance.PREFIX + ChatColor.GRAY + (
                                    p.getDisplayName().equals(p.getName()) ?
                                            p.getName() : p.getDisplayName() + ChatColor.DARK_GRAY + " (" + p.getName() + ")"
                            ) + ChatColor.GRAY +

                                    " -> " + content + " -┓"
                    ));
                } else {
                    for (String ic : IGNORE_LOGGING) {
                        if (args[0].equals(ic)) break logger;
                    }
                    p.sendMessage(new TextComponent(
                            instance.PREFIX + ChatColor.GRAY + (
                                    p.getDisplayName().equals(p.getName()) ?
                                            p.getName() : p.getDisplayName() + ChatColor.DARK_GRAY + " (" + p.getName() + ")"
                            ) + ChatColor.GRAY +

                                    " -> " + content
                    ));
                }
            }
        }
    }

    private boolean isIp(String susIp) {
        int t = 0;
        if (!(susIp.split("\\.").length == 4)) return false;

        for (String a : susIp.split("\\.")) {
            if (a.length() <= 3 && a.matches(".*\\d.*")) t++;
        }

        return t == 4;
    }

}
