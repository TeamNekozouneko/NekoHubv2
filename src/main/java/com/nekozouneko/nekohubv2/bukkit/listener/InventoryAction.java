package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InventoryAction implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().startsWith("NekoHub: ")) {
            if (e.getView().getTitle().equalsIgnoreCase("NekoHub: サーバーを選択")) {
                e.setCancelled(true);

                if (item.getType().equals(Material.CHEST)) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                        out.writeUTF(item.getItemMeta().getDisplayName().replace(ChatCode.GREEN, ""));

                        player.sendPluginMessage(instance, "nhv2:move", bytes.toByteArray());
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCursor();

        if (e.getView().getTitle().startsWith("NekoHub: ")) {
            if (e.getView().getTitle().equalsIgnoreCase("NekoHub: サーバーを選択")) {
                e.setCancelled(true);

                if (item.getType().equals(Material.CHEST)) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                        out.writeUTF(item.getItemMeta().getDisplayName().replace(ChatCode.GREEN, ""));

                        player.sendPluginMessage(instance, "nhv2:move", bytes.toByteArray());
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
            }
        }
    }

}
