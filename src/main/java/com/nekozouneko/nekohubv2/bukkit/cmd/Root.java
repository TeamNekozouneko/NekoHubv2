package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Root implements CommandExecutor, TabCompleter {

    private final NekoHubv2 instance = NekoHubv2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Status(sender, command, label, args);
        } else {
            if (args[0].equalsIgnoreCase("status")) {
                Status(sender, command, label, args);
            } else if (args[0].equalsIgnoreCase("reload")) {
                Reload(sender, command, label, args);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCmd = Arrays.asList("status", "reload");

            for (String sub : subCmd) {
                if (sub.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT))) tab.add(sub);
            }

        }

        return tab;
    }

    void Status(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(instance.PREFIX + "NekoHubv2 " + ChatColor.GRAY + "v"+instance.getDescription().getVersion() + " (S)");
        sender.sendMessage(instance.PREFIX + (instance.linked ? "プロキシと同期済み / " + instance.servers.size() + "個のサーバーが登録済み" : "プロキシと同期していません " + ChatColor.GRAY + "(鯖選択画面使用不可)"));
    }

    void Reload(CommandSender sender, Command command, String label, String[] args) {
        instance.reloadConfig();

        sender.sendMessage(instance.PREFIX + "設定ファイルを再読み込みしました。");
    }

}
