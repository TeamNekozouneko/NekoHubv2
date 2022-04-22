package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player plyer, byte[] message) {
        if (!channel.startsWith("nhv2:")) return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        if (channel.equalsIgnoreCase("nhv2:openseverpanel")) {
            try {
                String servers = in.readUTF();
                Player player = Bukkit.getPlayer(in.readUTF());

                Map<String, String> extServ = new HashMap<>();
                for (String serv : servers.split(";")) {
                    String[] serv2 = serv.split(":");

                    try {
                        extServ.put(serv2[0], serv2[1]);
                    } catch (Exception er) {
                        extServ.put(serv2[0], "説明なし");
                    }
                }

                initServerPanel(extServ, player);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 鯖リストをGUIで表示
     * @param serversMap サーバーのリストマップ
     * @param player GUIを開くプレイヤー
     */
    private void initServerPanel(Map<String, String> serversMap, Player player) {
        Inventory inv;
        if (serversMap.keySet().size() > 18) {
            inv = Bukkit.createInventory(player, 9*6, "NekoHub: サーバーを選択");
        } else {
            inv = Bukkit.createInventory(player, 9*3, "NekoHub: サーバーを選択");
        }

        for (String server : serversMap.keySet()) {
            ItemStack serverClickable = new ItemStack(Material.CHEST, 1);
            ItemMeta SCMeta = serverClickable.getItemMeta();

            SCMeta.setDisplayName(ChatCode.GREEN+server);


            List<String> lore = new ArrayList<>();
            lore.add(ChatCode.YELLOW       +"クリックして参加!");
            lore.add(ChatCode.STRIKETHROUGH+"--------------------");
            for (String lores : serversMap.get(server).split("\n")) {
                lore.add(ChatCode.AQUA + lores);
            }

            SCMeta.setLore(lore);

            serverClickable.setItemMeta(SCMeta);

            inv.addItem(serverClickable);

            player.openInventory(inv);
        }

    }
}
