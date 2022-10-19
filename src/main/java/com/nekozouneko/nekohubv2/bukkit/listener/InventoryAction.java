package com.nekozouneko.nekohubv2.bukkit.listener;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.nekozouneko.nekohubv2.bukkit.NekoHubv2.getInstance;

public class InventoryAction implements Listener {

    private NekoHubv2 instance = getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().startsWith("NekoHub: ") &&  item != null && item.getItemMeta() != null) {
            e.setCancelled(true);
            if (e.getView().getTitle().equalsIgnoreCase("NekoHub: サーバーを選択")) {
                if (item.getType().equals(Material.CHEST)) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                        out.writeUTF(item.getItemMeta().getDisplayName().replace(ChatColor.GREEN+"", ""));
                        player.closeInventory();
                        player.sendPluginMessage(instance, "nhv2:move", bytes.toByteArray());
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
            } else if (e.getView().getTitle().equalsIgnoreCase("NekoHub: メニュー")) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+player.getName())) {
                    ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta piSkull = (SkullMeta) playerInfo.getItemMeta();

                    piSkull.setDisplayName(ChatColor.GREEN+player.getName());

                    List<String> piLore = new ArrayList<>();
                    try {
                        double PlayedHours = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20.0 ) / 60.0 / 60.0;

                        piLore.add(ChatColor.GRAY + String.format("%.1f", PlayedHours) + "時間プレイ済み");
                    } catch (Exception ignored) {}
                    piLore.add(ChatColor.GRAY+(player.getPing()+"ms"));

                    try {
                        piSkull.setOwningPlayer(player);
                    } catch (IllegalArgumentException er) {
                        er.printStackTrace();
                    }

                    piSkull.setLore(piLore);
                    playerInfo.setItemMeta(piSkull);

                    e.getInventory().setItem(0, playerInfo);
                } else if (item.getItemMeta().getDisplayName().endsWith(ChatColor.GRAY + "[" + ChatColor.RED + "無効です" + ChatColor.GRAY + "]")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1f, 0f);
                    player.sendMessage(ChatColor.RED + "この機能は無効化されています。");
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"サーバー選択画面を開く")) {
                    player.closeInventory();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);

                    try {
                        out.writeUTF(player.getName());
                        out.writeBoolean(false);
                    } catch (IOException er) {
                        er.printStackTrace();
                    }

                    Bukkit.getScheduler().runTaskLater(instance, () -> {
                        player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());
                        player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 1f, 2f);
                    }, 10L);
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"最後に設定したスポーン地点に戻る")) {
                    if (player.getBedSpawnLocation() != null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.25f, 2f);

                        player.teleport(player.getBedSpawnLocation());
                    } else {
                        player.sendMessage(ChatColor.RED+"あなたはスポーン地点を設定してないため、テレポートできませんでした。");
                    }
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"エンダーチェストを開く")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(player.getEnderChest());
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"ゴミ箱を開く")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 1f);
                    player.openInventory(Bukkit.createInventory(null, 9*6, "ゴミ箱"));
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"プレイヤーの頭を入手")) {
                    if (player.getLevel() >= 15) {
                        AnvilGUI.Builder builder = new AnvilGUI.Builder();

                        builder.itemLeft(new ItemStack(Material.PAPER));
                        builder.plugin(instance);
                        builder.title("プレイヤー名を入力...");
                        builder.text("(例: nekozouneko / 例2: uuid:[UUID])");

                        builder.onComplete((player1, text) -> {
                            ItemStack player_head = new ItemStack(Material.PLAYER_HEAD, 16);
                            SkullMeta PHSMeta = (SkullMeta) player_head.getItemMeta();

                            if (text.startsWith("uuid:")) {
                                PHSMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(text.substring(5))));
                            } else {
                                PHSMeta.setOwningPlayer(Bukkit.getOfflinePlayer(text));
                            }

                            player_head.setItemMeta(PHSMeta);

                            player1.getInventory().addItem(player_head);
                            player1.playSound(player1.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                            player1.setLevel(player1.getLevel()-15);
                            return AnvilGUI.Response.close();
                        });

                        builder.open(player);
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1f, 2f);

                        player.sendMessage(ChatColor.RED+"最低でも15レベル以上必要です。");
                    }
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"初期スポーンに戻る")) {
                    player.teleport(Bukkit.getWorld(instance.defaultWorld).getSpawnLocation());
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1L, 0L);

                    player.closeInventory();
                } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"自殺する")) {
                    player.sendMessage(getInstance().PREFIX + "五秒後に自動的に自殺を実行します。 (動くことでキャンセル)");
                    new BukkitRunnable() {
                        Location loc = player.getLocation();
                        long x = (long) loc.getX();
                        long y = (long) loc.getY();
                        long z = (long) loc.getZ();
                        int i = 0;

                        @Override
                        public void run() {
                            Location loc0 = player.getLocation();
                            long x1 = (long) loc0.getX();
                            long y1 = (long) loc0.getY();
                            long z1 = (long) loc0.getZ();

                            if (x != x1 || y != y1 || z != z1) cancelAction();

                            if (i == 5 && !isCancelled()) {
                                player.setHealth(0.0);
                                player.sendMessage(getInstance().PREFIX + "自殺を実行しました。" + ChatColor.GRAY + "実行座標: " +
                                        String.format("%.1f", loc.getX()) + ", " +
                                        String.format("%.1f", loc.getY()) + ", " +
                                        String.format("%.1f", loc.getZ())
                                );
                                this.cancel();
                            }

                            i++;
                        }

                        public void cancelAction() {
                            player.sendMessage(getInstance().PREFIX + "自殺をキャンセルしました。");
                            this.cancel();
                        }
                    }.runTaskTimer(getInstance(), 20, 20);
                }
            }
        }
    }
}
