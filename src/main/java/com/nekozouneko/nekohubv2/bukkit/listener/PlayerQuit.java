package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.YELLOW + " " + e.getPlayer().getName()+"がゲームを退出しました");
        if (e.getQuitMessage().equalsIgnoreCase("Fly is not enabled on this server")) {
            for (Player p : instance.getServer().getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(instance.PREFIX + e.getPlayer().getName()+"がバニラの浮遊検知で追放されました。");
                }
            }
        }
    }

}
