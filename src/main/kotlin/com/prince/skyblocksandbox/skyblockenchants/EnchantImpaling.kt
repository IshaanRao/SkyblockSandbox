package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EnchantImpaling : SkyblockEnchant {
    override val levelRange: IntRange = 1..3
    override val name: String = "Impaling"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt to,","§7Squids and Guardians by","§a${12.5*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        if (mob.entityType == EntityType.GUARDIAN || mob.entityType == EntityType.SQUID) {
            return (0.125 * level)
        }
        return 0.0
    }
}