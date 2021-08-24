package com.prince.skyblocksandbox.skyblockutils;

import org.bukkit.ChatColor;

import java.util.Locale;

@SuppressWarnings("unused")
enum ActionBarColor{
    BLACK,
    DARK_BLUE,
    DARK_GREEN,
    DARK_AQUA,
    DARK_RED,
    DARK_PURPLE,
    GOLD,
    GRAY,
    DARK_GRAY,
    BLUE,
    GREEN,
    AQUA,
    RED,
    LIGHT_PURPLE,
    YELLOW,
    WHITE,
    MAGIC,
    BOLD,
    STRIKETHROUGH,
    UNDERLINE,
    ITALIC,
    RESET
    ;

    public ChatColor asBukkit(){
        return ChatColor.valueOf(name());
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}