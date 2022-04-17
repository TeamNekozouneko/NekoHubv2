package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nplib.NPLib;
import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickMenu implements CommandExecutor, TabCompleter {
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

    public static void initStickMenu(Player player) {
        Inventory StickMenu = Bukkit.createInventory(null, 3*9, "NekoHub: メニュー");

        /* Player info button **/
        ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta piSkull = (SkullMeta) playerInfo.getItemMeta();

        piSkull.setDisplayName(ChatCode.AQUA+player.getName());

        List<String> piLore = new ArrayList<>();

        double PlayedHours = (NPLib.tickToSecond(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 60) / 60;

        piLore.add(ChatCode.GRAY+String.format("%.1f", PlayedHours)+"時間プレイ済み");
        piLore.add(ChatCode.GRAY+player.getPing()+"ms / "+ChatCode.DARK_GRAY+player.getAddress().getAddress());

        try {
            piSkull.setOwnerProfile(player.getPlayerProfile());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        piSkull.setLore(piLore);
        playerInfo.setItemMeta(piSkull);

        /* Server selector button **/
        ItemStack serverSel = new ItemStack(Material.ENDER_EYE);
        ItemMeta ssMeta = serverSel.getItemMeta();

        ssMeta.setDisplayName(ChatCode.GREEN+"サーバー選択画面を開く");

        serverSel.setItemMeta(ssMeta);

        /* Last spawn point teleport button **/
        ItemStack LSButton = new ItemStack(Material.RED_BED);
        ItemMeta LSBMeta = LSButton.getItemMeta();

        LSBMeta.setDisplayName(ChatCode.GREEN+"最後に設定したスポーン地点に戻る");

        LSButton.setItemMeta(LSBMeta);

        /* EnderChest button **/
        ItemStack EnderChestButton = new ItemStack(Material.ENDER_CHEST);
        ItemMeta ECMeta = LSButton.getItemMeta();

        ECMeta.setDisplayName(ChatCode.GREEN+"エンダーチェストを開く");

        EnderChestButton.setItemMeta(ECMeta);

        /* Player head button **/
        ItemStack PlayerHeadButton = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta  PHBMeta = PlayerHeadButton.getItemMeta();

        PHBMeta.setDisplayName(ChatCode.GREEN+"プレイヤーの頭を入手");
        PHBMeta.setLore(Arrays.asList(ChatCode.RESET+ChatCode.GRAY+"その代わり1レベル"+ChatCode.RED+"消費"+ChatCode.GRAY+"します。"));

        PlayerHeadButton.setItemMeta(PHBMeta);

        /* Trash button **/
        ItemStack TrashButton = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta TMeta = TrashButton.getItemMeta();

        TMeta.setDisplayName(ChatCode.GREEN+"ゴミ箱を開く");
        TMeta.setLore(Arrays.asList(ChatCode.RED+"ゴミ箱に入れて閉じてしまった場合戻すことは"+ChatCode.DARK_RED+ChatCode.BOLD+"不可能"+ChatCode.RED+"です。"));

        TrashButton.setItemMeta(TMeta);

        // Line 1
        StickMenu.setItem(0, playerInfo);

        // Line 2
        StickMenu.setItem(10, serverSel);
        StickMenu.setItem(11, LSButton);
        StickMenu.setItem(12, EnderChestButton);
        StickMenu.setItem(13, PlayerHeadButton);
        StickMenu.setItem(16, TrashButton);

        player.openInventory(StickMenu);
    }

}
