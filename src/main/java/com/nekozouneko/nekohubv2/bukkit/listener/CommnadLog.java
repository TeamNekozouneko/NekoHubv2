package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommnadLog implements Listener {

    private final String[] IGNORE_LOGGING = new String[]{"/tell", "/w", "/reply", "/msg", "/m", "/t"};

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String content = e.getMessage();
        for (String arg : e.getMessage().split(" ")) {
            if (isIp(arg)) {
                content = content.replace(arg, "**.**.**.**");
            }
        }

        logger:for (Player p : instance.getServer().getOnlinePlayers()) {
            if (p.isOp() || p.hasPermission("nekohubv2.log.showlog")) {
                String[] args = e.getMessage().split(" ");

                if (e.getMessage().startsWith("/as") || e.getMessage().startsWith("/sudo") ||
                        e.getMessage().startsWith("/nekohubv2:as") || e.getMessage().startsWith("/nekohubv2:sudo") &&
                        args.length >= 3
                ) {
                    p.sendMessage(
                            instance.PREFIX + ChatColor.GRAY + (
                                    e.getPlayer().getDisplayName().equals(e.getPlayer().getName()) ?
                                            e.getPlayer().getName() : e.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + " (" + e.getPlayer().getName() + ")"
                                    ) + ChatColor.GRAY +

                            " -> " + content + " -┓"
                    );
                } else {
                    for (String ic : IGNORE_LOGGING) {
                        if (args[0].equalsIgnoreCase(ic)) break logger;
                    }
                    p.sendMessage(
                            instance.PREFIX + ChatColor.GRAY + (
                                    e.getPlayer().getDisplayName().equals(e.getPlayer().getName()) ?
                                            e.getPlayer().getName() : e.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + " (" + e.getPlayer().getName() + ")"
                            ) + ChatColor.GRAY +

                                    " -> " + content
                    );
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
