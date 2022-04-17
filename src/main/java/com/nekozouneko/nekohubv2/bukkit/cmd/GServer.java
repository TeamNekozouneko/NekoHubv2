package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import com.nekozouneko.nplib.chat.ChatCode;

public class GServer implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (args.length == 2) {
            player = instance.getServer().getPlayer(args[1]);
        } else {
            player = (Player) sender;
        }

        if (player == null) sender.sendMessage(ChatCode.RED+"そのようなプレイヤーは存在していません。");

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
        return null;
    }


}
