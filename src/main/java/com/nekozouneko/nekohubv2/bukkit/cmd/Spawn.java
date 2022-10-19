package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Spawn implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(instance.getConfig().getBoolean("menu.buttons.first_spawn", true))) {
            sender.sendMessage(ChatColor.RED + "この機能は無効化されています");
            return true;
        }


        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "プレイヤーとしてのみ実行可能です。");
            return true;
        }

        Player player = (Player) sender;

        player.teleport(Bukkit.getWorld(instance.defaultWorld).getSpawnLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1L, 0L);

        sender.sendMessage(instance.PREFIX + "初期スポーンにテレポートしました。");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
