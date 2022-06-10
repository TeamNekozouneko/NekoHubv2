package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Stick implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        try {
            player = (Player) sender;
        } catch (ClassCastException e) {
            sender.sendMessage(ChatCode.RED+"プレイヤーのみ実行できます。");
            return true;
        }

        boolean sf = false;

        for (ItemStack is : player.getInventory().getContents()) {
            if (is == null) continue;
            if (is.getType().equals(Material.STICK)) {
                sf = true;
                break;
            }
        }

        if (!sf) {
            ItemStack stick = new ItemStack(Material.STICK);

            player.getInventory().addItem(stick);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        } else {
            player.sendMessage(ChatCode.RED + "あなたのインベントリに棒が見つかったため取得はできません。");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
