package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object EnchantSharpness : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Sharpness"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases melee damage dealt by","§a${5*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        return (0.05*level)
    }
}