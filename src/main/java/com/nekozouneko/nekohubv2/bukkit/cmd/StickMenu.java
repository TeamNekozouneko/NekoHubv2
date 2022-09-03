package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickMenu implements CommandExecutor, TabCompleter {

    private static NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            initStickMenu((Player) sender);
        } catch (Exception e) {
            sender.sendMessage("プレイヤーとしてのみ実行できます。");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }

    /**
     * 棒メニューを初期化して開きます。
     * @param player 開くプレイヤー
     */
    public static void initStickMenu(Player player) {
        String disabledPrefix = ChatColor.GRAY + "[" + ChatColor.RED + "無効化されています" + ChatColor.GRAY + "]";
        FileConfiguration conf = instance.getConfig();
        Inventory StickMenu = Bukkit.createInventory(null, 3*9, "NekoHub: メニュー");

        /* Player info button **/
        ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta piSkull = (SkullMeta) playerInfo.getItemMeta();

        piSkull.setDisplayName(ChatColor.GREEN+player.getName());

        List<String> piLore = new ArrayList<>();
        try {
            double PlayedHours = ((player.getStatistic(Statistic.PLAY_ONE_MINUTE) * 20.0) / 60.0) / 60.0;

            piLore.add(ChatColor.GRAY + String.format("%.1f", PlayedHours) + "時間プレイ済み");
        } catch (Exception ignored) {}
        piLore.add(ChatColor.GRAY + (player.getPing()+"ms"));

        try {
            piSkull.setOwningPlayer(player);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        piSkull.setLore(piLore);
        playerInfo.setItemMeta(piSkull);

        /* Server selector button **/
        ItemStack serverSel = new ItemStack(Material.ENDER_EYE);
        ItemMeta ssMeta = serverSel.getItemMeta();

        ssMeta.setDisplayName(
            conf.getBoolean("menu.buttons.server_selector") ?
                ChatColor.GREEN+"サーバー選択画面を開く" :
                ChatColor.GREEN+"サーバー選択画面を開く " + disabledPrefix
        );

        serverSel.setItemMeta(ssMeta);

        /* Last spawn point teleport button **/
        ItemStack LSButton = new ItemStack(Material.RED_BED);
        ItemMeta LSBMeta = LSButton.getItemMeta();

        LSBMeta.setDisplayName(
            conf.getBoolean("menu.buttons.last_spawn") ?
                ChatColor.GREEN+"最後に設定したスポーン地点に戻る" :
                ChatColor.GREEN+"最後に設定したスポーン地点に戻る" + disabledPrefix

        );

        LSButton.setItemMeta(LSBMeta);

        /* EnderChest button **/
        ItemStack EnderChestButton = new ItemStack(Material.ENDER_CHEST);
        ItemMeta ECMeta = LSButton.getItemMeta();

        ECMeta.setDisplayName(
                conf.getBoolean("menu.buttons.ender_chest") ?
                        ChatColor.GREEN + "エンダーチェストを開く" :
                        ChatColor.GREEN+"エンダーチェストを開く" + disabledPrefix
        );

        EnderChestButton.setItemMeta(ECMeta);

        /* Player head button **/
        ItemStack PlayerHeadButton = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta  PHBMeta = PlayerHeadButton.getItemMeta();

        PHBMeta.setDisplayName(
                conf.getBoolean("menu.buttons.player_head") ?
                        ChatColor.GREEN+"プレイヤーの頭を入手" :
                        ChatColor.GREEN + "プレイヤーの頭を入手" + disabledPrefix
        );
        PHBMeta.setLore(Arrays.asList(ChatColor.RESET + (ChatColor.GRAY+"その代わり15レベル") +ChatColor.RED+"消費"+ChatColor.GRAY+"します。"));

        PlayerHeadButton.setItemMeta(PHBMeta);

        /* Trash button **/
        ItemStack TrashButton = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta TMeta = TrashButton.getItemMeta();

        TMeta.setDisplayName(
                conf.getBoolean("menu.buttons.trash_box") ?
                        ChatColor.GREEN + "ゴミ箱を開く" :
                        ChatColor.GREEN+"ゴミ箱を開く" + disabledPrefix
        );
        TMeta.setLore(Arrays.asList(ChatColor.RED+"ゴミ箱に入れて閉じてしまった場合戻すことは"+ChatColor.DARK_RED+ChatColor.BOLD+"不可能"+ChatColor.RED+"です。"));

        TrashButton.setItemMeta(TMeta);

        /* First spawn button **/
        ItemStack FSButton = new ItemStack(Material.ENDER_PEARL);
        ItemMeta  FSMeta = FSButton.getItemMeta();

        FSMeta.setDisplayName(
                conf.getBoolean("menu.buttons.first_spawn") ?
                        ChatColor.GREEN+"初期スポーンに戻る" :
                        ChatColor.GREEN + "初期スポーンに戻る" + disabledPrefix
        );

        FSButton.setItemMeta(FSMeta);

        // Line 1
        StickMenu.setItem(0, playerInfo);

        // Line 2
        StickMenu.setItem(10, serverSel);
        StickMenu.setItem(11, LSButton);
        StickMenu.setItem(12, EnderChestButton);
        StickMenu.setItem(13, PlayerHeadButton);
        StickMenu.setItem(16, TrashButton);
        StickMenu.setItem(14, FSButton);

        player.openInventory(StickMenu);
    }

}
