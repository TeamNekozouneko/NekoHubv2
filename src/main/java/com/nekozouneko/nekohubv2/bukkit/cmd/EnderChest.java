package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class EnderChest implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(instance.getConfig().getBoolean("menu.buttons.ender_chest"))) {
            sender.sendMessage(ChatColor.RED + "この機能は無効化されています。");
            return true;
        }
        Player player;
        try {
            player = (Player) sender;
        } catch (ClassCastException e) {
            sender.sendMessage(ChatColor.RED+"このコマンドはプレイヤーとしてのみ実行可能です。");
            return true;
        }

        Inventory ender = player.getEnderChest();

        player.openInventory(ender);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 1f);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
