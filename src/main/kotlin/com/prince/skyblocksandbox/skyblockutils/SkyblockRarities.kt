package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.ChatColor

enum class SkyblockRarities(var sbcolor: String) {
    COMMON("§7"),
    UNCOMMON("§a"),
    RARE("§9"),
    EPIC("§5"),
    LEGENDARY("§6"),
    MYTHIC("§d"),
    SUPREME("§4"),
    SPECIAL("§c"),
    VERY_SPECIAL("§c");

    fun getColor(): String {
        return sbcolor
    }

    override fun toString() : String {
        return name.replace("_", "")
    }
}