package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nplib.chat.ChatCode;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(ChatCode.YELLOW + " " + e.getPlayer().getName()+"がゲームを退出しました");
    }

}
