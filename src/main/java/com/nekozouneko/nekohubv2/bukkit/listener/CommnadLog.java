package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommnadLog implements Listener {

    private final String[] IGNORE_LOGGING = new String[]{"/tell", "/w", "/reply", "/msg", "/m", "/t"};

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        logger:for (Player p : instance.getServer().getOnlinePlayers()) {
            if (p.isOp() || p.hasPermission("nekohubv2.log.showlog")) {
                String[] args = e.getMessage().split(" ");

                if (e.getMessage().startsWith("/as") || e.getMessage().startsWith("/sudo") ||
                        e.getMessage().startsWith("/nekohubv2:as") || e.getMessage().startsWith("/nekohubv2:sudo") &&
                        args.length >= 3
                ) {
                    p.sendMessage(
                            instance.PREFIX + ChatCode.GRAY + (
                                    e.getPlayer().getDisplayName().equals(e.getPlayer().getName()) ?
                                            e.getPlayer().getName() : e.getPlayer().getDisplayName() + ChatCode.DARK_GRAY + " (" + e.getPlayer().getName() + ")"
                                    ) + ChatCode.GRAY +

                            " -> " + e.getMessage() + " -┓"
                    );
                } else {
                    for (String ic : IGNORE_LOGGING) {
                        if (e.getMessage().startsWith(ic)) break logger;
                    }
                    p.sendMessage(
                            instance.PREFIX + ChatCode.GRAY + (
                                    e.getPlayer().getDisplayName().equals(e.getPlayer().getName()) ?
                                            e.getPlayer().getName() : e.getPlayer().getDisplayName() + ChatCode.DARK_GRAY + " (" + e.getPlayer().getName() + ")"
                            ) + ChatCode.GRAY +

                                    " -> " + e.getMessage()
                    );
                }
            }
        }
    }

}
