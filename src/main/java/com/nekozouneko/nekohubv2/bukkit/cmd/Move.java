package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (args.length == 2) {
            player = instance.getServer().getPlayer(args[1]);
        } else {
            try {
                player = (Player) sender;
            } catch (ClassCastException e) {
                sender.sendMessage(ChatColor.RED+"プレイヤーとしてのみ実行できます。");
                return true;
            }
        }

        if (player == null) sender.sendMessage(ChatColor.RED+"そのようなプレイヤーは存在していません。");

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF(player.getName()); // white player name
            out.writeUTF(args[0]); // write move server name

            sender.getServer().sendPluginMessage(instance, "nhv2:move", bytes.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return instance.servers;
        } else if (args.length == 2) {
            List<String> tab = new ArrayList<>();

            for (Player ply : instance.getServer().getOnlinePlayers()) {
                String now = ply.getName();
                if (now.toUpperCase().startsWith(args[1].toUpperCase())) {
                    tab.add(ply.getName());
                }
            }

            return tab;
        }

        return new ArrayList<>();
    }


}
