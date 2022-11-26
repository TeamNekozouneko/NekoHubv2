package com.nekozouneko.nekohubv2.bukkit.cmd;

import com.nekozouneko.nekohubv2.bukkit.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class As implements CommandExecutor, TabCompleter {

    private NekoHubv2 instance = NekoHubv2.getInstance();
    

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "プレイヤーまたはテキスト/コマンドを入力してください。");

            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        

        String content = "";
        int i = 1;

        for (String a : args) {
            if (i == 1) {
                i++;
                continue;
            }

            content = content + a + " ";
        }

        content = content.substring(0, content.length()-1);

        player.chat(content);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) tab.add(p.getName());
            }
        } else if (args.length == 2) {
            if (args[1].startsWith("/")) {
                for (Plugin p : instance.getServer().getPluginManager().getPlugins()) {
                    for (String c : p.getDescription().getCommands().keySet()) {
                        if (c.toLowerCase().startsWith(args[1].toLowerCase().substring(1))) tab.add(c);
                    }
                }
            }
        }

        return tab;
    }

}
