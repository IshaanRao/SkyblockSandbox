package com.prince.skyblocksandbox.skyblockutils

enum class SkyblockRarities(var sbcolor: String) {
    COMMON("§7"),UNCOMMON("§a"),RARE("§9"),EPIC("§5"),LEGENDARY("§6"),MYTHIC("§d");

    fun getColor(): String {
        return sbcolor
    }
}