package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.Color


object ColorUtils {
    fun hex2Rgb(colorStr: String): Color {
        return Color.fromRGB(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16)
        )
    }
}