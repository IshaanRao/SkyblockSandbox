package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.ChatColor

enum class ActionBarColor{
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

    fun asBukkit() : ChatColor {
        return ChatColor.valueOf(name)
    }

    operator fun plus(other: String) : String {
        return toString()+other
    }

    override fun toString(): String {
        return name.lowercase()
    }
}