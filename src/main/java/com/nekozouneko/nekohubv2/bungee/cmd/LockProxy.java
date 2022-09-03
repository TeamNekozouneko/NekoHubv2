package com.nekozouneko.nekohubv2.bungee.cmd;

import com.nekozouneko.nekohubv2.bungee.NekoHubv2;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class LockProxy extends Command {
    private NekoHubv2 instance = NekoHubv2.getInstance();

    public LockProxy() {
        super("lock-proxy", "nekohubv2.cmd.lock-proxy");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!this.instance.NOW_LOCKED) {
            this.instance.NOW_LOCKED = true;
            sender.sendMessage(new TextComponent(this.instance.PREFIX + "プロキシをロックしました。"));
        } else {
            this.instance.NOW_LOCKED = false;
            sender.sendMessage(new TextComponent(this.instance.PREFIX + "プロキシのロックを解除しました。"));
        }

    }
}