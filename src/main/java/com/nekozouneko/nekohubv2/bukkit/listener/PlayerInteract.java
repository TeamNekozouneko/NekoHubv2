package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nekohubv2.bukkit.cmd.StickMenu;

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
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            try {
                out.writeUTF(player.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            StickMenu.initStickMenu(player);
        }
    }

}
