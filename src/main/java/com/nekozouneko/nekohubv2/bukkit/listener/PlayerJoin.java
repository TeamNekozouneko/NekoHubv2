package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.cmd.Rule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getLastPlayed() == 0) {
            e.setJoinMessage(" " + ChatColor.AQUA + ">" + ChatColor.GREEN + ">" + ChatColor.YELLOW + ">" + ChatColor.GOLD + " 初見さんの" + e.getPlayer().getName()+"がゲームに参加しました! よろです!");
        } else if (e.getPlayer().isOp()){
            e.setJoinMessage(" " + ChatColor.AQUA + ">" + ChatColor.GREEN + ">" + ChatColor.YELLOW + ">" + ChatColor.GOLD + " オペレーターの" + e.getPlayer().getName()+"がゲームに参加しました!");
        } else {
            e.setJoinMessage(ChatColor.YELLOW + " " + e.getPlayer().getName()+"がゲームに参加しました!");
        }

        e.getPlayer().sendMessage(Rule.getRule());
    }

}
