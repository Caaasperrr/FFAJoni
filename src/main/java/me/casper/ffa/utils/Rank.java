package me.casper.ffa.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER("§4Owner", "§4Owner §8|§4 ", "0000000Owner", "owner"),
    ADMIN("§cAdmin", "§cAdmin §8|§c ", "0000001Admin", "admin"),

    DEVELOPER_PLUS("§bDev+", "§bDev+ §8|§b ", "0000002Dev+", "developer_plus"),
    DEVELOPER("§bDev", "§bDev §8|§b ", "0000003Dev", "developer"),

    BUILDER_PLUS("§2Builder+", "§2Build+ §8|§2 ", "0000004Builder+", "builder_plus"),
    BUILDER("§2Builder", "§2Build §8|§2 ", "0000005Builder", "builder"),

    DESIGNER_PLUS("§9Designer+", "§9Design+ §8|§9 ", "0000006Designer+", "designer_plus"),
    DESIGNER("§9Designer", "§9Design §8|§9 ", "0000007Designer", "designer"),

    MODERATOR_PLUS("§cMod+", "§cMod+ §8|§c ", "0000008Mod+", "moderator_plus"),
    MODERATOR("§cMod", "§cMod §8|§c ", "0000009Mod", "moderator"),
    //    YOUTUBER("§5Youtuber", "§5", "0000006YouTuber", "youtuber"),

    MEDIA_PLUS("§5Youtuber", "§5", "0000010Media+", "media_plus"),
    MEDIA("§dMedia", "§d", "0000030Media", "media"),

    PRESTIGE("§4Prestige", "§4", "0000040Prestige", "prestige"),
    EXPERT("§bExpert", "§b", "0000050Expert", "expert"),
    MVP_PLUS("§aMVP§d+", "§aMVP§d+ §8|§b ", "0000060MVP+", "mvp_plus"),
    MVP("§aMVP", "§a", "0000070MVP", "mvp"),
    PRO_PLUS("§6Pro+", "§6", "0000080PRO+", "vip+"),
    PRO("§ePro", "§e", "0000090PRO", "vip"),
    PLAYER("§7Player", "§7", "0000100Player", "default");

    private final String name;
    private final String prefix;
    private final String team;
    private final String groupName;

}
