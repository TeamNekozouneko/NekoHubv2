package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerPanel implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = instance.getServer().getPlayer(sender.getName());
        if (player == null) {
            sender.sendMessage(ChatCode.RED+"プレイヤーとしてのみ実行可能です。");
            return true;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        try {
            out.writeUTF(player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(ChatCode.GREEN+"サーバー選択画面を開いています...");
        player.sendPluginMessage(instance, "nhv2:requestserverpanel", bytes.toByteArray());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }

}
