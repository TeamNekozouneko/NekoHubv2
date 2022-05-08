package com.nekozouneko.nekohubv2.bungee.files;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UserData {

    info info = new info();

    String syncEnder;

    public UserData(ProxiedPlayer player, String ender) {
        info.setUUID(player.getUniqueId().toString());
        info.setDisplayName(player.getName());
        info.setNickname(player.getDisplayName());

        syncEnder = ender;
    }

    public UserData(String UUID, String DisplayName, String Nickname, String ender) {
        info.setUUID(UUID);
        info.setDisplayName(DisplayName);
        info.setNickname(Nickname);

        syncEnder = ender;
    }

    public class info {

        String UUID;

        String DisplayName;
        String Nickname;

        public info() {
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public void setDisplayName(String DN) {
            this.DisplayName = DN;
        }

        public void setNickname(String NN) {
            this.Nickname = NN;
        }
    }

}
