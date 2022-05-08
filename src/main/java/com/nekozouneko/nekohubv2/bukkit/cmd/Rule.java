package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import com.nekozouneko.nplib.chat.ChatCode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Rule implements CommandExecutor, TabCompleter {

    private static NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(getRule());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

    public static String getRule() {
        return ChatCode.toColorCode("&", instance.getConfig().getString("rule"));
    }
}
