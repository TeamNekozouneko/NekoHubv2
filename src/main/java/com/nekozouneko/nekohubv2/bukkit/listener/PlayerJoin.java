package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getLastPlayed() == 0) {
            e.setJoinMessage(" " + ChatCode.AQUA + ">" + ChatCode.GREEN + ">" + ChatCode.YELLOW + ">" + ChatCode.GOLD + " 初見さんの" + e.getPlayer().getName()+"がゲームに参加しました! よろです!");
        } else if (e.getPlayer().isOp()){
            e.setJoinMessage(" " + ChatCode.AQUA + ">" + ChatCode.GREEN + ">" + ChatCode.YELLOW + ">" + ChatCode.GOLD + " オペレーターの" + e.getPlayer().getName()+"がゲームに参加しました!");
        } else {
            e.setJoinMessage(ChatCode.YELLOW + " " + e.getPlayer().getName()+"がゲームに参加しました!");
        }
    }

}
