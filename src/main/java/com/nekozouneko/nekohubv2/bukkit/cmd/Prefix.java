package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PrefixNode;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nekozouneko.nekohubv2.bukkit.NekoHubv2.getInstance;

public class Prefix implements CommandExecutor, TabCompleter {

    LuckPerms lp = NekoHubv2.luckPerms;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getInstance().PREFIX);
            return true;
        }
        Player player = (Player) sender;
        User user = lp.getPlayerAdapter(Player.class).getUser(player);

        if (args.length == 1) {
            sender.sendMessage(getInstance().PREFIX + "現在のプレフィックスは「" + user.getCachedData().getMetaData().getPrefix() + "§r」です");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(getInstance().PREFIX + ChatColor.RED + "使用方法: /prefix <target> <string>");
            return true;
        }

        String arg = String.join(" ", args).substring(args[0].length()+1);

        Matcher m = Pattern.compile("\"(.*?)\"").matcher(arg);
        m.find(0);

        user.data().add(PrefixNode.builder(ChatColor.translateAlternateColorCodes('&', m.group(1)), 9).build());
        lp.getUserManager().saveUser(user);

        sender.sendMessage(getInstance().PREFIX + "プレフィックスを「" + ChatColor.translateAlternateColorCodes('&', m.group(1)) + "§r」にセットしました");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> tab = new ArrayList<>();

            for (Player ply : getInstance().getServer().getOnlinePlayers()) {
                String now = ply.getName();
                if (now.toUpperCase().startsWith(args[0].toUpperCase())) {
                    tab.add(ply.getName());
                }
            }

            return tab;
        }

        return new ArrayList<>();
    }
}
