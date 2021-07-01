package com.prince.skyblocksandbox.skyblockutils

enum class SkyblockColors(var s: String) {
    RED("§c"),BLUE("§9"),GREEN("§a"),BOLD("");

    override fun toString(): String {
        return s
    }
}