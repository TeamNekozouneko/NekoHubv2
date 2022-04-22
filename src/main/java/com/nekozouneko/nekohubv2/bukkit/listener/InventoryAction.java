package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nplib.NPLib;
import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryAction implements Listener {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().startsWith("NekoHub: ")) {
            e.setCancelled(true);
            if (e.getView().getTitle().equalsIgnoreCase("NekoHub: サーバーを選択")) {
                if (item != null && item.getType().equals(Material.CHEST)) {
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
            } else if (e.getView().getTitle().equalsIgnoreCase("NekoHub: メニュー")) {
                if (item != null && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.AQUA+player)) {
                    ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta piSkull = (SkullMeta) playerInfo.getItemMeta();

                    piSkull.setDisplayName(ChatCode.AQUA+player.getName());

                    List<String> piLore = new ArrayList<>();

                    double PlayedHours = (NPLib.tickToSecond(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 60) / 60;

                    piLore.add(ChatCode.GRAY+String.format("%.1f", PlayedHours)+"時間プレイ済み");
                    piLore.add(ChatCode.GRAY+player.getPing()+"ms / "+ChatCode.DARK_GRAY+player.getAddress().getAddress());

                    try {
                        piSkull.setOwnerProfile(player.getPlayerProfile());
                    } catch (IllegalArgumentException er) {
                        er.printStackTrace();
                    }

                    piSkull.setLore(piLore);
                    playerInfo.setItemMeta(piSkull);

                    e.getInventory().setItem(0, playerInfo);
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN+"サーバー選択画面を開く")) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                    } catch (IOException er) {
                        er.printStackTrace();
                    }

                    player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN+"最後に設定したスポーン地点に戻る")) {
                    if (player.getBedSpawnLocation() != null) {
                        player.closeInventory();
                        player.teleport(player.getBedSpawnLocation());
                    } else {
                        player.sendMessage(ChatCode.RED+"あなたはスポーン地点を設定してないため、テレポートすることができません。");
                    }
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN+"エンダーチェストを開く")) {
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(player.getEnderChest());
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN+"ゴミ箱を開く")) {
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(Bukkit.createInventory(null, 9*6, "ゴミ箱"));
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN+"プレイヤーの頭を入手")) {
                    if (player.getLevel() >= 1) {
                        player.setLevel(player.getLevel()-1);
                        ItemStack player_head = new ItemStack(Material.PLAYER_HEAD, 64);
                        SkullMeta PHSMeta = (SkullMeta) player_head.getItemMeta();

                        PHSMeta.setOwnerProfile(player.getPlayerProfile());

                        player_head.setItemMeta(PHSMeta);

                        player.getInventory().addItem(player_head);
                        player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                    } else {
                        player.sendMessage(ChatCode.RED+"最低でも1レベル以上必要です。");
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
            e.setCancelled(true);
            if (e.getView().getTitle().equalsIgnoreCase("NekoHub: サーバーを選択")) {
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
            } else if (e.getView().getTitle().equalsIgnoreCase("NekoHub: メニュー")) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.AQUA + player)) {
                    ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta piSkull = (SkullMeta) playerInfo.getItemMeta();

                    piSkull.setDisplayName(ChatCode.AQUA + player.getName());

                    List<String> piLore = new ArrayList<>();

                    double PlayedHours = (NPLib.tickToSecond(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 60) / 60;

                    piLore.add(ChatCode.GRAY + String.format("%.1f", PlayedHours) + "時間プレイ済み");
                    piLore.add(ChatCode.GRAY + player.getPing() + "ms / " + ChatCode.DARK_GRAY + player.getAddress().getAddress());

                    try {
                        piSkull.setOwnerProfile(player.getPlayerProfile());
                    } catch (IllegalArgumentException er) {
                        er.printStackTrace();
                    }

                    piSkull.setLore(piLore);
                    playerInfo.setItemMeta(piSkull);

                    e.getInventory().setItem(0, playerInfo);
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN + "サーバー選択画面を開く")) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                    } catch (IOException er) {
                        er.printStackTrace();
                    }

                    player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN + "最後に設定したスポーン地点に戻る")) {
                    if (player.getBedSpawnLocation() != null) {
                        player.closeInventory();
                        player.teleport(player.getBedSpawnLocation());
                    } else {
                        player.sendMessage(ChatCode.RED + "あなたはスポーン地点を設定してないため、テレポートすることができません。");
                    }
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN + "エンダーチェストを開く")) {
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(player.getEnderChest());
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN + "ゴミ箱を開く")) {
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(Bukkit.createInventory(null, 9 * 6, "ゴミ箱"));
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatCode.GREEN + "プレイヤーの頭を入手")) {
                    if (player.getLevel() >= 1) {
                        player.setLevel(player.getLevel() - 1);
                        ItemStack player_head = new ItemStack(Material.PLAYER_HEAD, 64);
                        SkullMeta PHSMeta = (SkullMeta) player_head.getItemMeta();

                        PHSMeta.setOwnerProfile(player.getPlayerProfile());

                        player_head.setItemMeta(PHSMeta);

                        player.getInventory().addItem(player_head);
                        player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                    } else {
                        player.sendMessage(ChatCode.RED + "最低でも1レベル以上必要です。");
                    }
                }
            }
        }
    }
}
