package com.nekozouneko.nekohubv2.bungee.cmd;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Root extends Command implements TabExecutor {

    private NekoHubv2 instance = NekoHubv2.getInstance();

    public Root(String name, String... aliases) {
        super(name, "NekoHubv2.cmd.nekohubv2", aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Status(sender, args);
        } else {
            if (args[0].equalsIgnoreCase("status")) {
                Status(sender, args);
            } else if (args[0].equalsIgnoreCase("sync")) {
                Sync(sender, args);
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> tab = new ArrayList<>();
        List<String> SUB_COMMANDS = Arrays.asList("status", "sync");

        if (args.length == 1) {
            for (String s : SUB_COMMANDS) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    tab.add(s);
                }
            }
        }

        return tab;
    }

    void Status(CommandSender sender, String[] args) {
        sender.sendMessage(new TextComponent(instance.PREFIX + "NekoHubv2 " + ChatColor.GRAY + "v"+instance.getDescription().getVersion() + " (B)"));
        sender.sendMessage(new TextComponent(instance.PREFIX + (NekoHubv2.NOW_LOCKED ? "新規アクセス禁止中" + ChatColor.GRAY + "(/lock-proxy)" : "新規アクセス可能")));
        sender.sendMessage(new TextComponent(instance.PREFIX + (NekoHubv2.getGInstance() != null ? "Geyserと連携中" + ChatColor.GRAY + "(統合版のフォームが有効)" : "Geyserと未連携")));
    }

    void Sync(CommandSender sender, String[] args) {
        sender.sendMessage(new TextComponent(instance.PREFIX + "同期中です..."));
        instance.getLogger().info("Server data is being sent...");

        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        DataOutputStream out1 = new DataOutputStream(bytes1);

        try {
            String servers = "";
            for (String K : instance.getProxy().getServers().keySet()) {
                servers += K + ";";
            }

            out1.writeUTF(servers);
        } catch (IOException er) {
            er.printStackTrace();
            sender.sendMessage(new TextComponent(instance.PREFIX + ChatColor.RED + "同期に失敗しました。"));
            return;
        }

        for (String key : instance.getProxy().getServers().keySet()) {
            instance.getProxy().getServers().get(key).sendData("nhv2:sync", bytes1.toByteArray());
        }

        instance.getLogger().info("Sent.");
        sender.sendMessage(new TextComponent(instance.PREFIX + "同期が完了しました。"));
    }
}
