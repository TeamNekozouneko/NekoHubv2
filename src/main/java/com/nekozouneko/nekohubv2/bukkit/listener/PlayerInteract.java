package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nekohubv2.bukkit.cmd.StickMenu;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerInteract implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        try {
            switch (event.getItem().getType()) {
                case STICK:
                    openStickGUI(player, event);
                default:
                    return;
            }
        } catch (Exception ignored) {}
    }

    /**
     * 棒メニューを開きます。
     * @param player 棒メニューを開くプレイヤー
     * @param event @EventHandlerで出たPlayerInteractEvent
     */
    private void openStickGUI(Player player, PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 1L, 2L);
            if (instance.getConfig().getBoolean("menu.buttons.server_selector")) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bytes);

                try {
                    out.writeUTF(player.getName());
                    if (player.isSneaking()) {
                        out.writeBoolean(true);
                    } else out.writeBoolean(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());
            } else {
                player.sendMessage(ChatColor.RED + "この機能は無効化されています。");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1L, 0L);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1L, 0L);
            StickMenu.initStickMenu(player);
        }
    }

}
